pipeline {
  agent any

  // Use the tool names configured in Manage Jenkins > Tools
  tools {
    jdk   'jdk-21'
    maven 'maven-3.9.11'
  }

  options {
    timestamps()
    disableConcurrentBuilds()
    timeout(time: 30, unit: 'MINUTES')
  }

  environment {
    BASE_URL = 'https://www.td.com/ca/en/personal-banking'
    HEADLESS = 'true'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test (Headless Chrome)') {
      steps {
        // Runs your tests the same way you ran them locally
        bat 'mvn -B -Dheadless=%HEADLESS% -DbaseUrl="%BASE_URL%" clean test'
      }
    }

    stage('Publish Reports') {
      steps {
        // JUnit results so Jenkins can show pass/fail counts
        junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true

        // Cucumber HTML report – robust paths
        // If your report is Report/cucumber/index.html -> first block works
        // If your report is Report/cucumber.html -> second block works
        script {
          if (fileExists('Report/cucumber/index.html')) {
            publishHTML([
              reportDir: 'Report/cucumber',
              reportFiles: 'index.html',
              reportName: 'Cucumber UI Report',
              keepAll: true,
              alwaysLinkToLastBuild: true,
              allowMissing: true
            ])
          } else if (fileExists('Report/cucumber.html')) {
            publishHTML([
              reportDir: 'Report',
              reportFiles: 'cucumber.html',
              reportName: 'Cucumber UI Report',
              keepAll: true,
              alwaysLinkToLastBuild: true,
              allowMissing: true
            ])
          } else {
            echo 'Cucumber HTML file not found under Report/.'
          }
        }
      }
    }
  }

  post {
    always {
      // Keep both the raw report and surefire xmls
      archiveArtifacts artifacts: 'Report/**, target/**', fingerprint: true
    }
  }
}
