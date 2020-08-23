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
                             def runTryScript = "./deploy-try.sh"
                             def runCatchScript = "./deploy-catch.sh"
                        try {
                                sshagent(credentials : ['uat']) {
                                    sh "ssh -o StrictHostKeyChecking=no ubuntu@3.16.163.202 ${runTryScript}"
                                }
                            } catch (Throwable e) {
                                sshagent(credentials : ['uat']) {
                					sh "ssh -o StrictHostKeyChecking=no ubuntu@3.16.163.202 ${runCatchScript}"
                				}
                			}
                        }
                }
        }
    }
}
