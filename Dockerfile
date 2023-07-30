# Use a suitable base image for Java and Spring Boot applications
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file to the container
COPY target/Kanban-0.0.1-SNAPSHOT.jar /app/

# Set the command to run your Spring Boot app
CMD ["java", "-jar", "Kanban-0.0.1-SNAPSHOT.jar"]