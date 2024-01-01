pipeline {
  agent { label 'Jenkins-Agent' }
  tools {
    jdk 'Java17'
    maven 'Maven3'
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
  }
}
