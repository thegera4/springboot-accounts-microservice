# Download the openjdk (Java) image from Docker Hub
FROM openjdk:17-jdk-slim

# Information about who maintains the image
LABEL "org.opencontainers.image.authors"="jgmedellin.com"

# Copy the JAR file from the target directory to the root directory of the docker container
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]