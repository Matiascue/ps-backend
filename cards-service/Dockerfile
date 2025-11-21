FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar card-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/card-service.jar"]