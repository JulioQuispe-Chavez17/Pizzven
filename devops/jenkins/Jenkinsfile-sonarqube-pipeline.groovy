pipeline {
    agent any
    stages {

        stage("Git Clone"){
            steps {
                cleanWs()
                    checkout([$class: 'GitSCM', 
                    branches: [[name: '*/main']], 
                    doGenerateSubmoduleConfigurations: false, 
                    extensions: [[$class: 'CleanCheckout']], 
                    submoduleCfg: [], 
                    userRemoteConfigs: [
                        [url: 'https://github.com/JulioQuispe-Chavez17/Pizzven.git', credentialsId: 'jenkins_github']
                        ]])
                sh 'pwd' 
                sh 'ls -l'
            } //steps
        }  //stage

        stage('Build Project') {
            agent any
            steps {
                    sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            agent any
            steps {
                    sh 'mvn clean verify sonar:sonar -Dsonar.host.url=http://35.222.30.201:9400 -Dsonar.login=5a14b0d5d47a7c7a80925c32643f8a0ff97b23ba'
            }
        }

    }
}
