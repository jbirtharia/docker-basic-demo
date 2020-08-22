pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/jbirtharia/docker-basic-demo.git'

                // Run Maven on a Unix agent.
                sh "mvn package -Dmaven.test.skip=true\n"
            }

        }
    }
}
