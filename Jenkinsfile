pipeline {
    agent any

    stages {
        stage('checkout') {
            steps {
                checkout scm
            }
        }
        stage('check mvn version') {
            steps {
                sh 'mvn --version'
            }
        }
        stage('test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('sonarqube') {
    steps {
        sh 'mvn sonar:sonar -Dsonar.host.url=http://monitor101.eastus.cloudapp.azure.com -Dsonar.login=sqa_833edfdf90868d4432282043a2c4051e7fd65b1d'
            }
        }

    }

    post {
        success {
            archiveArtifacts(artifacts: '**/target/*.jar', allowEmptyArchive: false)
        }
    }
}
