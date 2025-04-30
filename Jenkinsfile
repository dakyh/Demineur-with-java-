pipeline {
    agent any

    tools {
        sonarQubeScanner 'sonar-scanner' // Doit correspondre au nom défini dans Jenkins
    }

    stages {
        stage('Analyse SonarQube') {
            steps {
                withSonarQubeEnv('local-sonarqube') { // Doit correspondre au nom défini dans Jenkins
                    bat 'sonar-scanner'
                }
            }
        }
    }
}
