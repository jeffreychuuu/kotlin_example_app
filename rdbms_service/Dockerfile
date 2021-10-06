FROM openjdk:11

WORKDIR /app
COPY build/libs/rdbms_service-0.0.1-SNAPSHOT.jar .rdbms_service.jar
COPY application.yml .

CMD ["java", "-jar", "rdbms_service.jar"]
EXPOSE 8080