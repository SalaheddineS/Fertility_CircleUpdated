pipeline {
    agent any
    tools {
        // Define the JDK tool to use
        jdk 'JDK 17'
    }
    stages {
        stage('Build') {
            steps {
                // Your build steps here
                sh 'java -version' // This will use the configured JDK 17 installation
            }
        }
        // Add more stages as needed
    }
}
