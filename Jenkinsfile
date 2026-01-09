pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

//         stage('SonarQube Analysis') {
//             steps {
//                 withSonarQubeEnv('sonar') {
//                     sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
//                 }
//             }
//         }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ganeshlonare/spring-boot-crud:latest .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    sh '''
                      echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                      docker push ganeshlonare/spring-boot-crud:latest
                      docker logout
                    '''
                }
            }
        }


    }

    post {
        success {
            echo 'CI + SonarQube analysis successful'
        }
        failure {
            echo 'Pipeline failed'
        }
    }
}
