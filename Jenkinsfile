pipeline {
    agent any
    
    tools {
        maven 'Maven'
    }
    
    environment {
        SLACK_WEBHOOK = credentials('slack-webhook')
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '==================== Checkout Stage ===================='
                git branch: 'main',
                    url: 'https://github.com/YOUR_USERNAME/Projet-DevOps-YourName.git'
                echo 'Code récupéré avec succès depuis GitHub'
            }
        }
        
        stage('Build') {
            steps {
                echo '==================== Build Stage ===================='
                sh 'mvn clean compile'
                echo 'Build terminé avec succès'
            }
        }
        
        stage('Test') {
            steps {
                echo '==================== Test Stage ===================='
                sh 'mvn test'
                echo 'Tests exécutés avec succès'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo '==================== Package Stage ===================='
                sh 'mvn package'
                echo 'Package créé avec succès'
            }
        }
        
        stage('Archive') {
            steps {
                echo '==================== Archive Stage ===================='
                archiveArtifacts artifacts: 'target/*.jar', 
                                 fingerprint: true,
                                 allowEmptyArchive: false
                echo 'Artifacts archivés avec succès'
            }
        }
        
        stage('Deploy') {
            steps {
                echo '==================== Deploy Stage ===================='
                sh '''
                    echo "Déploiement de l'application..."
                    java -jar target/mini-projet-devops-1.0-SNAPSHOT.jar
                '''
                echo 'Application déployée et exécutée avec succès'
            }
        }
        
        stage('Notify Slack') {
            steps {
                echo '==================== Slack Notification ===================='
                script {
                    def message = """
                        {
                            "text": "success Pipeline Jenkins - Build Réussi",
                            "blocks": [
                                {
                                    "type": "header",
                                    "text": {
                                        "type": "plain_text",
                                        "text": "success Build Réussi - ${env.JOB_NAME}"
                                    }
                                },
                                {
                                    "type": "section",
                                    "fields": [
                                        {
                                            "type": "mrkdwn",
                                            "text": "*Projet:*\\n${env.JOB_NAME}"
                                        },
                                        {
                                            "type": "mrkdwn",
                                            "text": "*Build:*\\n#${env.BUILD_NUMBER}"
                                        },
                                        {
                                            "type": "mrkdwn",
                                            "text": "*Statut:*\\nSUCCESS success"
                                        },
                                        {
                                            "type": "mrkdwn",
                                            "text": "*Durée:*\\n${currentBuild.durationString}"
                                        }
                                    ]
                                },
                                {
                                    "type": "section",
                                    "text": {
                                        "type": "mrkdwn",
                                        "text": "<${env.BUILD_URL}|Voir les détails du build>"
                                    }
                                }
                            ]
                        }
                    """
                    
                    sh """
                        curl -X POST -H 'Content-type: application/json' \
                        --data '${message}' \
                        ${SLACK_WEBHOOK}
                    """
                }
                echo 'Notification Slack envoyée avec succès'
            }
        }
    }
    
    post {
        success {
            echo '==================== Pipeline Terminé avec Succès ===================='
        }
        failure {
            echo '==================== Pipeline Échoué ===================='
            script {
                def message = """
                    {
                        "text": "error: Pipeline Jenkins - Build Échoué",
                        "blocks": [
                            {
                                "type": "header",
                                "text": {
                                    "type": "plain_text",
                                    "text": "error: Build Échoué - ${env.JOB_NAME}"
                                }
                            },
                            {
                                "type": "section",
                                "fields": [
                                    {
                                        "type": "mrkdwn",
                                        "text": "*Projet:*\\n${env.JOB_NAME}"
                                    },
                                    {
                                        "type": "mrkdwn",
                                        "text": "*Build:*\\n#${env.BUILD_NUMBER}"
                                    },
                                    {
                                        "type": "mrkdwn",
                                        "text": "*Statut:*\\nFAILURE error:"
                                    }
                                ]
                            },
                            {
                                "type": "section",
                                "text": {
                                    "type": "mrkdwn",
                                    "text": "<${env.BUILD_URL}console|Voir les logs>"
                                }
                            }
                        ]
                    }
                """
                
                sh """
                    curl -X POST -H 'Content-type: application/json' \
                    --data '${message}' \
                    ${SLACK_WEBHOOK}
                """
            }
        }
    }
}