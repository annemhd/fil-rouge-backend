# Fil Rouge Application

Ce projet est en Java Spring Boot et est connecté à MYSQL. Il est contenu sous Docker.

## 1. Pré-requis

### Pour l'équipe backend

Avant de commencer à développer, il faut installer :

-   [Java 17](https://adoptium.net/?variant=openjdk17)
-   [Maven](https://maven.apache.org/install.html)
-   [Docker](https://docs.docker.com/get-docker/)
-   [Docker Compose](https://docs.docker.com/compose/install/)

## 2. Project Structure

```sh
my-springboot-app/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/
│ │ │ └── filrougeapp/
│ │ │ └── FilRougeApplication.java
│ │ └── resources/
│ │ └── application.yml
├── target/
│ └── fil-rouge-app.jar
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## 3. Build et lancer l'application

Suivre ces étapes pour build et lancer l'application :

### 1. Cloner le repository

```sh
git clone <repository-url>
cd fil-rouge-backend
```

### 2. Build l'application

Vérifier que le dossier 'target' existe et contient bien le fichier fil-rouge-app-1.0.0.jar
Si le fichier 'target' n'existe pas, lancer la commande `mvn clean package`

Si tout est correct, lancer la commande `docker-compose up --build`

L'application se lancera sur http://localhost:8080.
