# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target directory into the container
COPY mobilleWallet-0.0.1-SNAPSHOT.jar /app/mobilleWallet.jar

# Make port 8080 available
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/mobilleWallet.jar"]