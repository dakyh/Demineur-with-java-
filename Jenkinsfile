pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_TOKEN = credentials('sqp_295dbfa079040a21227285f4d96b45ac48921bc9') // ← récupéré depuis Jenkins credentials
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
