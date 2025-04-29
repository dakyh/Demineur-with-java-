pipeline {
    agent any

    tools {
        sonarQubeScanner 'sonar-scanner' // nom configuré dans Jenkins
    }

    stages {
        stage('Cloner le dépôt') {
            steps {
                git 'https://github.com/ton-utilisateur/ton-depot.git'
            }
        }

        stage('Analyse SonarQube') {
            steps {
                withSonarQubeEnv('local-sonarqube') {
                    sh 'sonar-scanner'
                }
            }
        }
    }
}
