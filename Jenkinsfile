pipeline {
  agent any

  tools {
    jdk   'jdk-21'
    maven 'maven-3.9'   // <-- must match Manage Jenkins > Tools > Maven name
  }

  environment {
    BASE_URL   = 'https://www.td.com/ca/en/personal-banking'
    HEADLESS   = 'false'

    // 🔽 GCP integration values (your real project + bucket)
    GCP_PROJECT = 'my-fdm-jenkinsproject'
    GCS_BUCKET  = 'aiswarya-jenkins-reports'
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
        // JUnit results for Jenkins test trend
        junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true

        // Find & publish the real Cucumber HTML report
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

    // 🔽 NEW: Authenticate Jenkins to GCP using your JSON service account
    stage('GCP Auth') {
      steps {
        withCredentials([file(credentialsId: 'gcp-sa-key', variable: 'GOOGLE_APPLICATION_CREDENTIALS')]) {
          bat """
          gcloud auth activate-service-account --key-file="%GOOGLE_APPLICATION_CREDENTIALS%"
          gcloud config set project %GCP_PROJECT%
          """
        }
      }
    }

    // 🔽 NEW: Upload test artifacts to your GCS bucket
    stage('Upload Reports to GCS') {
      steps {
        withCredentials([file(credentialsId: 'gcp-sa-key', variable: 'GOOGLE_APPLICATION_CREDENTIALS')]) {
          bat """
          gcloud auth activate-service-account --key-file="%GOOGLE_APPLICATION_CREDENTIALS%"
          gcloud config set project %GCP_PROJECT%

          REM Create a demo file to prove the integration works
          echo Jenkins to GCP integration successful > jenkins-gcp-demo.txt

          REM Upload the demo file
          gsutil cp jenkins-gcp-demo.txt gs://%GCS_BUCKET%/

          REM OPTIONAL: Upload Maven test reports if they exist
          IF EXIST target\\surefire-reports (
              gsutil cp target\\surefire-reports\\* gs://%GCS_BUCKET%/surefire-reports/
          )
          """
        }
      }
    }
  }
}
