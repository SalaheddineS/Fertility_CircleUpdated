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
        stage('sonarqube') {
            steps {
                sh "./mvnw verify sonar:sonar -Dsonar.login=sqa_bf671c72e819670061c2bbcd53de75f138132e4c"
            }
        }
    }

    post {
        success {
            archiveArtifacts(artifacts: '**/target/*.jar', allowEmptyArchive: false)
        }
    }
}
