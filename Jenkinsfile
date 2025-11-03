pipeline {
  agent any

  tools {
    jdk 'jdk-21'            
    maven 'maven-3.9'       
  }

  environment {
    BASE_URL = "https://www.td.com/ca/en/personal-banking"
    HEADLESS = "true"
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
        publishHTML([
          allowMissing: true,
          keepAll: true,
          reportDir: 'Report',
          reportFiles: 'cucumber.html',
          reportName: 'Cucumber UI Report'
        ])
        junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'Report/**, target/**', fingerprint: true
    }
  }
}
