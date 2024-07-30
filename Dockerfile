# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file into the container at /app
COPY target/fil-rouge-app-1.0.0.jar /app/app.jar

# Copy the startup script with delay
COPY config.sh /app/config.sh

# Make the startup script executable
RUN chmod +x /app/config.sh

# Make port 8080 available to the world outside this container
EXPOSE 8080

# # Run the jar file
# ENTRYPOINT ["java", "-jar", "app.jar"]

# Use the startup script as the entrypoint
ENTRYPOINT ["/app/config.sh"]
