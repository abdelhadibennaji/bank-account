pipeline {
  agent { label 'Jenkins-Agent' }
  tools {
    jdk 'Java17'
    maven 'Maven3'
  }
  environment {
	APP_NAME = "bank-account-app-pipeline"
        RELEASE = "0.0.1-SNAPSHOT"
        DOCKER_USER = "abdelhadibennaji"
        DOCKER_PASS = 'dockerhub'
        IMAGE_NAME = "${DOCKER_USER}" + "/" + "${APP_NAME}"
        IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"
  }
  stages {
    stage('Cleanup Workspace') {
      steps {
        cleanWs()
      }
    }
    
    stage('Checkout from SCM') {
      steps {
        git branch: 'master', credentialsId: 'github', url: 'https://github.com/abdelhadibennaji/bank-account'
      }
    }

    stage('Build application') {
      steps {
        sh "mvn clean package"
      }
    }

    stage('Test application') {
      steps {
        sh "mvn test"
      }
    }

    stage("SonarQube Analysis"){
           steps {
	           script {
		        withSonarQubeEnv(credentialsId: 'jenkins-sonarqube-token') { 
                        sh "mvn sonar:sonar"
		           }
	           }	
           }
    }


    stage("Quality Gate"){
           steps {
               script {
                    waitForQualityGate abortPipeline: false, credentialsId: 'jenkins-sonarqube-token'
                }	
            }
        }

  stage("Build & Push Docker Image") {
            steps {
                script {
                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image = docker.build "${IMAGE_NAME}"
                    }
                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image.push("${IMAGE_TAG}")
                        docker_image.push('latest')
                    }
                }
            }
       }

   stage("Trivy Scan") {
           steps {
               script {
	            sh ('docker run -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy image abdelhadibennaji/bank-account-app-pipeline:latest --no-progress --scanners vuln  --exit-code 0 --severity HIGH,CRITICAL --format table')
               }
           }
       }

  stage ('Cleanup Artifacts') {
           steps {
               script {
                    sh "docker rmi ${IMAGE_NAME}:${IMAGE_TAG}"
                    sh "docker rmi ${IMAGE_NAME}:latest"
               }
          }
   }
	  
  }
}
