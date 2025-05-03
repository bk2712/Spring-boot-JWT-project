# Use OpenJDK 17 as base image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy your JAR file into the container
COPY target/Test-jwt-methods-0.0.1-SNAPSHOT.jar app.jar

# Expose the port (Render will dynamically assign this as $PORT)
EXPOSE 8080

# Run the JAR with dynamic port binding from Render
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
