#!/bin/sh

# Attendre 30 seconds avant que l'application Spring Boot se lance pour permettre que mysql se lance avant
sleep 30

# Lancer l'application Spring Boot
java -jar /app/app.jar
