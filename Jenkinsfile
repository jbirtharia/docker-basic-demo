pipeline {
    agent any

    environment {
        DOCKER_PASS = credentials('jenkins-aws-secret-key-id')
    }

    stages {
        stage('Git') {
            steps {

                // Pulling latest repository from git
                git 'https://github.com/jbirtharia/docker-basic-demo.git'

            }

        }
        stage('Build'){
            steps{
                // Building jar file
                sh '''
                    echo Maven Build Start...
                    mvn package -Dmaven.test.skip=true
                    
                 '''
            }
        }
        stage('Pushing'){
            steps{
                // Pushing image into docker hub
                sh '''
                    DOCKER_TAG=docker images jbirtharia/docker-basic-demo| tail -n +2 | awk '{print $2}'
                    
                    echo "***********************"
                    echo "*Pushing Process Start*"
                    echo "************************"

                    echo "*****Logging In**********"
                    docker login -u jbirtharia -p ${DOCKER_PASS}
                    echo ********Pushing Image******"
                    docker push jbirtharia/docker-basic-demo:$DOCKER_TAG
                    
                 '''
            }
        }
    }
}
