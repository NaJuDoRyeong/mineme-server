FROM arm64v8/openjdk:11

WORKDIR /app/

COPY ./build/libs/server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080 

ENTRYPOINT ["java","-jar","app.jar"]
