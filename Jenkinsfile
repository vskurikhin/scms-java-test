pipeline {
    agent any // Runs the pipeline on any available agent/node

    stages {
        stage('Build') {
            steps {
                // Ensure the gradlew script has executable permissions
                sh 'chmod +x gradlew'
                // Run the 'build' task using the Gradle wrapper
                sh './gradlew build'
                sh 'env'
            }
        }

        stage('Test') {
            steps {
                // Run the 'test' task specifically, if desired, though 'build' often includes it
                sh './gradlew test'
                sh 'sleep 900'
            }
            // Optional: Archive test results (e.g., JUnit format)
            post {
                always {
                    junit 'build/test-results/test/**/*.xml'
                }
            }
        }

        // Add more stages like 'Deploy', 'Staging', etc., as needed
    }
}