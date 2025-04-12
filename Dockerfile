
#Start with a base image of conyaining java runtime enviroment
FROM openjdk:17-jdk-slim


#Information about who maintains the jar
MAINTAINER Shiva.com

#Add application jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar


#Execute the application
ENTRYPOINT [ "java","-jar","accounts-0.0.1-SNAPSHOT.jar" ]