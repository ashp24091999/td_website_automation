stage('Publish Reports') {
  steps {
    // JUnit so Jenkins shows test counts
    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true

    // Find the real Cucumber HTML report and publish it
    script {
      // Add/adjust candidates if your structure is different
      def candidates = [
        'Report/cucumber/index.html',                 // cucumber/ index.html
        'Report/cucumber.html',                       // single file in Report
        'Report/index.html',                          // generic index.html in Report
        'Report/cucumber/cucumber.html',              // some generators do this
        'Report/cucumber-reports/overview-features.html' // popular plugin name
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
        error "Could not find a Cucumber HTML report. Checked:\n - " + candidates.join('\n - ')
      }
    }

    // Keep raw files too
    archiveArtifacts artifacts: 'Report/**, target/**', fingerprint: true
  }
}
