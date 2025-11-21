FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar user-service.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]