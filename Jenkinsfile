pipeline {
    agent any

    environment {
        GITHUB_URL = "https://github.com/ndsdarkwalker/phuctnh1-jwd-project4"
//         GITHUB_CREDETIALS = credentials("github-NAME")
        BRANCH = "main"
    }

    stages {
        stage("Clone Code") {
            steps {
                git url: "${GITHUB_URL}",
                        branch: "${BRANCH}",
                        credentialsId: "${GITHUB_URL}"
            }
        }
        stage("Build") {
            steps {
                sh "mvn clean install"
            }
            post {
                always {
                    junit "target/surefire-reports/*.xml"
                }
            }
        }
    }
}
