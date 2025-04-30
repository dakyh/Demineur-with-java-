pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_TOKEN = credentials('SONAR_TOKEN')
    }

    stages {
        stage('Analyse SonarQube') {
            steps {
                bat """
                    sonar-scanner.bat ^
                    -Dsonar.projectKey=demineur ^
                    -Dsonar.projectName=demineur ^
                    -Dsonar.sources=. ^
                    -Dsonar.host.url=%SONAR_HOST_URL% ^
                    -Dsonar.login=%SONAR_TOKEN%
                """
            }
        }
    }
}
