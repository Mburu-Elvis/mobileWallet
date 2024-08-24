# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR mobilleWallet/mobilleWallet/

# Copy the JAR file from the target directory into the container
COPY target/mobilleWallet-0.0.1-SNAPSHOT.jar /app/mobilleWallet.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/mobilleWallet.jar"]
