# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper and project files into the container
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code into the container
COPY src src

# Run Maven to build the project
RUN ./mvnw clean package -DskipTests

# Copy the JAR file from the target directory into the container
COPY target/mobilleWallet-0.0.1-SNAPSHOT.jar /app/mobilleWallet.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/mobilleWallet.jar"]