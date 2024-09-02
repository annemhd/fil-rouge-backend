# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file into the container at /app
COPY target/fil-rouge-app-1.0.0.jar /app/app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# # Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

