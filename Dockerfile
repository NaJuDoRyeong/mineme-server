FROM openjdk:11-jre-slim

WORKDIR /app/

EXPOSE 8080 

ENTRYPOINT ["java","-jar","server-0.0.1-SNAPSHOT.jar"]