pipeline {
  agent any

  tools {
    jdk   'jdk-21'       // keep this name as you set it
    maven 'maven-3.9'    // <-- use this (not maven-3.9.11)
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
      steps { checkout scm }
    }

    stage('Build & Test (Headless Chrome)') {
      steps {
        bat 'mvn -B -Dheadless=%HEADLESS% -DbaseUrl="%BASE_URL%" clean test'
      }
    }

    stage('Publish Reports') {
      steps {
        junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
        script {
          if (fileExists('Report/cucumber/index.html')) {
            publishHTML([
              reportDir: 'Report/cucumber',
              reportFiles: 'index.html',
              reportName: 'Cucumber UI Report',
              keepAll: true, alwaysLinkToLastBuild: true, allowMissing: true
            ])
          } else if (fileExists('Report/cucumber.html')) {
            publishHTML([
              reportDir: 'Report',
              reportFiles: 'cucumber.html',
              reportName: 'Cucumber UI Report',
              keepAll: true, alwaysLinkToLastBuild: true, allowMissing: true
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
      archiveArtifacts artifacts: 'Report/**, target/**', fingerprint: true
    }
  }
}
