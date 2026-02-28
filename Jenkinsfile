pipeline {
    agent any // Runs the pipeline on any available agent/node
    environment {
        GH_TOKEN = credentials('GH_TOKEN')
    }

    stages {
        stage('Build') {
            steps {
                sh 'env'
                // Ensure the gradlew script has executable permissions
                sh 'chmod +x gradlew'
                // Run the 'build' task using the Gradle wrapper
                sh './gradlew build -x test'
            }
        }

        stage('Test') {
            steps {
                // Run the 'test' task specifically, if desired, though 'build' often includes it
                sh './gradlew test'
                sh 'gh release list'
                sh 'sleep 60'
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