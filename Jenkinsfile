pipeline {
  agent any

  tools {
    jdk   'jdk-21'
    maven 'maven-3.9'
  }

  environment {
    BASE_URL   = 'https://www.td.com/ca/en/personal-banking'
    HEADLESS   = 'false'

    GCP_PROJECT = 'my-fdm-jenkinsproject'
    GCS_BUCKET  = 'aiswarya-jenkins-reports'

    // Path where Google Cloud SDK is installed (from: where gcloud)
    GCLOUD_PATH = 'C:\\Users\\ashp2\\AppData\\Local\\Google\\Cloud SDK\\google-cloud-sdk\\bin'
  }

  stages {

    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build & Test (Headless)') {
      steps {
        bat 'mvn -B -Dheadless=%HEADLESS% -DbaseUrl="%BASE_URL%" clean test'
      }
    }

    stage('Publish Reports') {
      steps {
        // JUnit results
        junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true

        // Find & publish Cucumber HTML report
        script {
          def candidates = [
            'Report/cucumber/index.html',
            'Report/cucumber.html',
            'Report/index.html',
            'Report/cucumber/cucumber.html',
            'Report/cucumber-reports/overview-features.html'
          ]
          def found = candidates.find { fileExists(it) }
          if (found) {
            echo "Publishing Cucumber report: ${found}"
            def dir  = found.substring(0, found.lastIndexOf('/'))
            def file = found.substring(found.lastIndexOf('/') + 1)
            publishHTML([
              reportDir: dir,
              reportFiles: file,
              reportName: 'Cucumber UI Report',
              keepAll: true,
              alwaysLinkToLastBuild: true,
              allowMissing: false
            ])
          } else {
            echo "No Cucumber HTML found in: ${candidates}"
          }
        }

        // Keep raw artifacts too
        archiveArtifacts artifacts: 'Report/**, target/**', fingerprint: true
      }
    }

    // Authenticate with GCP (service account)
    stage('GCP Auth') {
      steps {
        withCredentials([file(credentialsId: 'gcp-sa-key', variable: 'GOOGLE_APPLICATION_CREDENTIALS')]) {
          bat """
          set PATH=%GCLOUD_PATH%;%PATH%
          gcloud auth activate-service-account --key-file="%GOOGLE_APPLICATION_CREDENTIALS%"
          gcloud config set project %GCP_PROJECT%
          """
        }
      }
    }

    // Upload test outputs to GCP bucket
    stage('Upload Reports to GCS') {
      steps {
        withCredentials([file(credentialsId: 'gcp-sa-key', variable: 'GOOGLE_APPLICATION_CREDENTIALS')]) {
          bat """
          echo === Upload Reports to GCS stage starting ===

          set PATH=%GCLOUD_PATH%;%PATH%
          echo PATH is: %PATH%

          echo Authenticating again inside upload stage...
          gcloud auth activate-service-account --key-file="%GOOGLE_APPLICATION_CREDENTIALS%"
          gcloud config set project %GCP_PROJECT%

          echo Creating demo file...
          echo Jenkins to GCP integration successful > jenkins-gcp-demo.txt

          echo Uploading demo file with gsutil...
          "%GCLOUD_PATH%\\gsutil.cmd" cp jenkins-gcp-demo.txt gs://%GCS_BUCKET%/

          echo Listing bucket contents after upload...
          "%GCLOUD_PATH%\\gsutil.cmd" ls gs://%GCS_BUCKET%/**

          echo Uploading surefire reports if present...
          IF EXIST target\\surefire-reports (
              "%GCLOUD_PATH%\\gsutil.cmd" cp target\\surefire-reports\\* gs://%GCS_BUCKET%/surefire-reports/
          )

          echo === Upload Reports to GCS stage finished ===
          """
        }
      }
    }
  }
}
