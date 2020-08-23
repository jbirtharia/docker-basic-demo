pipeline {
    agent any

    environment {
        DOCKER_PASS = credentials('DOCKER_PASS')
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
                    echo "*****Logging In**********"
                    docker login -u jbirtharia -p ${DOCKER_PASS}
                    echo "*****Pushing To Dockerhub****"

                    DOCKER_TAG=$(docker images jbirtharia/docker-basic-demo| tail -n +2 | awk '{print $2}'| head -1)

                    docker push jbirtharia/docker-basic-demo:$DOCKER_TAG
                    
                 '''
            }
        }
        stage ('Deploy') {
            steps{
                //Deploying on uat server by pulling image from dockerhub
                script {
                        try {
                                sshagent(credentials : ['uat']) {
                                    sh '''
                                        echo "**********************************************************************"
                                        echo "			              CREATING CONTAINER                            "
                                        echo "**********************************************************************"
                                        docker container stop app
                                        docker container rm app
                                        docker image pull jbirtharia/docker-basic-demo:$(curl -s -S "https://registry.hub.docker.com/v2/repositories/jbirtharia/docker-basic-demo/tags/" | jq '."results"[]["name"]'|sed -n 1p|tr -d '"')
                                        docker run -d --name app -p 80:8080 jbirtharia/docker-basic-demo:$(curl -s -S "https://registry.hub.docker.com/v2/repositories/jbirtharia/docker-basic-demo/tags/" | jq '."results"[]["name"]'|sed -n 1p|tr -d '"')
                                        docker logs -f app &> app.log &
                                       '''
                                    }
                            } catch (Throwable e) {
                                sshagent(credentials : ['uat']) {
                					sh '''
                                        docker image pull jbirtharia/docker-basic-demo:$(curl -s -S "https://registry.hub.docker.com/v2/repositories/jbirtharia/docker-basic-demo/tags/" | jq '."results"[]["name"]'|sed -n 1p|tr -d '"')
                                        docker run -d --name app -p 80:8080 jbirtharia/docker-basic-demo:$(curl -s -S "https://registry.hub.docker.com/v2/repositories/jbirtharia/docker-basic-demo/tags/" | jq '."results"[]["name"]'|sed -n 1p|tr -d '"')
                                        docker logs -f app &> app.log &
                                       '''
                					}
                			}
                        }
                }
        }
    }
}
