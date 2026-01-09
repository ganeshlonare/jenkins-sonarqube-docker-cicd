pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        IMAGE_NAME = 'ganeshlonare/spring-boot-crud'
        TIMESTAMP  = "${new Date().format('yyyy-MM-dd-HHmm')}"
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

        // SonarQube
        /*
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
                }
            }
        }
        */

        stage('Backup Previous Docker Image (if exists)') {
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

                      if docker pull $IMAGE_NAME:latest; then
                        echo "Previous image found. Tagging with timestamp..."
                        docker tag $IMAGE_NAME:latest $IMAGE_NAME:$TIMESTAMP
                        docker push $IMAGE_NAME:$TIMESTAMP
                      else
                        echo "No previous image found. First run."
                      fi

                      docker logout
                    '''
                }
            }
        }

        stage('Build New Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME:latest .'
            }
        }

        stage('Push New Docker Image (latest)') {
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
                      docker push $IMAGE_NAME:latest
                      docker logout
                    '''
                }
            }
        }
    }

    post {
        success {
            echo "CI successful. Previous image backed up as: $TIMESTAMP"
        }

        failure {
            echo "Pipeline failed. Restoring previous Docker image as latest..."

            withCredentials([
                usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )
            ]) {
                sh '''
                  echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin

                  if docker pull $IMAGE_NAME:$TIMESTAMP; then
                    docker tag $IMAGE_NAME:$TIMESTAMP $IMAGE_NAME:latest
                    docker push $IMAGE_NAME:latest
                    echo "Rollback complete: latest now points to previous image"
                  else
                    echo "Rollback skipped: no backup image found"
                  fi

                  docker logout
                '''
            }
        }
    }
}
