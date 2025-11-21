FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar notification-services.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/notification-services.jar"]