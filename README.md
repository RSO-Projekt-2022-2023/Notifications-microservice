# RSO: Notifications microservice

## Prerequisites

```bash
docker run -d --name pg-image-metadata -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=image-metadata -p 5432:5432 postgres:13
```

## Build and run commands
```bash
mvn clean package
cd api/target
java -jar notifications-api-1.0.0-SNAPSHOT.jar
```
Available at: localhost:8080/v1/notifications

## Run in IntelliJ IDEA
Add new Run configuration and select the Application type. In the next step, select the module api and for the main class com.kumuluz.ee.EeApplication.

Available at: localhost:8080/v1/notifications

## Docker commands
```bash
docker build -t notifications .   
docker images
docker run notifications    
docker docker tag notifications xineeeee/rso   
docker docker push xineeeee/rso
docker ps
```

## Kubernetes
```bash
kubectl version
kubectl --help
kubectl get nodes
kubectl create -f notifications-deployment.yaml 
kubectl apply -f notifications-deployment.yaml 
kubectl get services 
kubectl get deployments
kubectl get pods
kubectl logs notifications-deployment-6f59c5d96c-rjz46
kubectl delete notifications-deployment-6f59c5d96c-rjz46
```
Secrets: https://kubernetes.io/docs/concepts/configuration/secret/

