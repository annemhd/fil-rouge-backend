#!/bin/sh

mvn clean package

# Attendre 20 seconds avant que l'application Spring Boot se lance pour permettre que mysql se lance avant
sleep 20




# Lancer l'application Spring Boot
java -jar /app/app.jar
