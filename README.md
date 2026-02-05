# jenkins-k8s-helm

# deploy jenkins
```bash
# docker compose -f ./jenkins/docker-compose.yml up 

# deploy jenkins with local mailserver
docker compose up
```

# Local Cluster Management
1. Create the cluster
```bash
 kind create cluster --config ./kind/cluster.yaml --name k8s-cluster

 # set the created cluster as context if not already
 kind export kubeconfig --name k8s-cluster
 ```

2. base64 encode the kubeconfig file

```Powershell
#Powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("C:\Users\ashrafur.minhaj\.kube\config"))
```

* clearnup
```bash
 kind delete cluster
 kind delete clusters --all
```

 # tunnel out local jenkins to public url
 - ssh -R 80:localhost:8080 serveo.net

 ## Setup jenkins for github webhook trigger

- Create a Jenkins build job that uses a GitHub URL
- Click the GitHub hook trigger for GITScm polling checkbox on the build job
- Create and copy a Jenkins API token for the Jenkins user who will run the build job
- Create a trigger in your GitHub repository’s settings page
- Set the GitHub payload URL to be your Jenkins’ IP address with /github-webhook/ appended to it
- Set the Jenkins API token as the GitHub webhook secret token
- Save the GitHub Webhook and then Jenkins builds will occur when a commit is pushed to the repo

sample pipeline
```groovy
@Library('custom-libs') _

pipeline {
    agent {
        label 'k8s-agent' //  K8s pod template label
    }

    stages {
        stage('log agent pod hostname') {
            steps {
                sh 'hostname'
            }
        }

        stage('Checkout Source') {
            steps {
                git url: 'https://github.com/enominhaj/test-app.git', branch: 'main'
            }
        }

        stage('Run Go Tests') {
            steps {
                script {
                    // Call your shared lib function
                    go_test('app') // pass the directory if needed
                }
            }
        }
    }
}
```

# mail server
[mailhog](https://github.com/mailhog/MailHog)

Sample pipeline with error -
```groovy
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo "Running build..."
                sh 'exit 1'   // force failure
            }
        }
    }

    post {
        failure {
            mail(
                to: 'test@example.com',
                subject: "❌ Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
Job        : ${env.JOB_NAME}
Build No   : ${env.BUILD_NUMBER}
Status     : FAILED
Node       : ${env.NODE_NAME}

Failure reason:
The shell step returned a non-zero exit code.

Build URL:
${env.BUILD_URL}

Check the console log for full details.
"""
            )
        }
    }
}
```

jenkin plugins: mailer, email extension plugin

# build agent
docker run --rm -it enominhaj/build-agent:v1 
