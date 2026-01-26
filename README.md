# jenkins-k8s-helm

# deploy jenkins
```bash
docker compose -f ./jenkins/docker-compose.yml up 
```

# Local Cluster Management
1. Create the cluster
```bash
 kind create cluster --config ./kind/cluster.yaml --name k8s-cluster
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