FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar chat-service.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "/app/chat-service.jar"]