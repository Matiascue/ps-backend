FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar sales-buy.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/sales-buy.jar"]