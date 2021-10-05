FROM openjdk:11

WORKDIR /app
COPY build/libs/mongo_service-0.0.1-SNAPSHOT.jar .mongo_service.jar
COPY application.yml .

CMD ["java", "-jar", "mongo_service.jar"]
EXPOSE 8080