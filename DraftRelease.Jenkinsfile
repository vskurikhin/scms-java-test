pipeline {
    agent any // Runs the pipeline on any available agent/node
    environment {
        GH_LOGIN = credentials('GH_LOGIN')
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
                sh "awk -F '=' '\$1~/^version/{print\$2}' build.gradle > VERSION"
                sh "sed -i \"s/ //g;s/'//g\" VERSION"
                sh 'echo -n "VERSION: " ; cat VERSION'
                sh 'git tag "$(cat VERSION)"'
                sh 'git remote set-url origin $(git remote get-url origin | sed -e "s|//.*github|//${GH_LOGIN}:${GH_TOKEN}@github|")'
                sh 'git push origin "$(cat VERSION)"'
                sh 'gh release create --draft "$(cat VERSION)" --title "$(cat VERSION)" --notes "Jenkins Multiple SCMs project for Spring Boot"'
                sh 'gh release upload "$(cat VERSION)" ./build/libs/scms-java-test-$(cat VERSION).jar'
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