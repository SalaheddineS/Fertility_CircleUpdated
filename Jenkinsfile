pipeline {
    agent any

    stages {
        stage('Check Java Version') {
            steps {
                sh 'java -version'
            }
        }

        /* Commented out stages:
        stage('Build') {
            steps {
                // Your build steps here
            }
        }

        stage('SonarQube') {
            steps {
                // Your SonarQube steps here
            }
        }

        stage('Deploy') {
            steps {
                // Your deployment steps here
            }
        }
        */
    }

    post {
        failure {
            echo "Build failed, see above for details."
        }
    }
}
