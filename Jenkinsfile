pipeline {
    agent any

    tools {
        sonarQubeScanner 'sonar-scanner' // ← doit être le nom que tu as mis dans Jenkins
    }

    stages {
        stage('Analyse SonarQube') {
            steps {
                withSonarQubeEnv('local-sonarqube') { // ← doit être le nom défini dans Jenkins > Configure System
                    bat 'sonar-scanner.bat'
                }
            }
        }
    }
}
