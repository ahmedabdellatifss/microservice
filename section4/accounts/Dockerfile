#Start with a base image containing Java runtime
FROM openjdk:23-jdk-slim

#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="https://github.com/ahmedabdellatifss"

#Add the application's jar to the container
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/accounts-0.0.1-SNAPSHOT.jar"]