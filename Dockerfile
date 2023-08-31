# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container at the working directory
COPY target/api-pessoas-0.0.1-SNAPSHOT.jar /app/api-pessoas-0.0.1-SNAPSHOT.jar

# Install curl
RUN apt-get update && apt-get install -y curl

# Expose the port that the application will listen on
EXPOSE 8080

# JVM Options: Adjust as needed based on your application's requirements
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:MaxMetaspaceSize=256m -XX:+UseG1GC -XX:ParallelGCThreads=2"

# Run the application
CMD ["java", "-jar", "api-pessoas-0.0.1-SNAPSHOT.jar"]
