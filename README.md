# jenkins-k8s-helm

# deploy jenkins
```bash
docker compose -f ./jenkins/docker-compose.yml up 
```

# Local Cluster Management
1. Create the cluster
```bash
 kind create cluster --config ./kind/cluster.yaml --name my-cluster
 ```

 