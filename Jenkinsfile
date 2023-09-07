pipeline {
    agent any

    stages {
        stage('checkout') {
            steps {
                checkout scm
            }
        }
        stage('test') {
            steps {
                sh './mvnw clean test'
            }
        }
        stage('build') {
            steps {
                sh './mvnw clean install -DskipTests'
            }
        }
        stage('java'){
            steps {
                sh 'java -version'
            }
        }
      /*  stage('sonarqube') {
            steps {
                script {
                    // Access the sonarcommand environment variable
                    def sonarCommand = env.sonarcommand
                    sh sonarCommand
                }
            }
        }*/
    }

    post {
        success {
            archiveArtifacts(artifacts: '**/target/*.jar', allowEmptyArchive: false)
        }
    }
}
