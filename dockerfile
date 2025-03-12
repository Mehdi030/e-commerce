# 1. Base Image
FROM openjdk:17-jdk-slim

# 2. Arbeitsverzeichnis setzen
WORKDIR /app

# 3. JAR-Datei kopieren
COPY build/libs/shopping-cart.jar app.jar

# 4. Port für Spring Boot freigeben
EXPOSE 8080

# 5. Startbefehl setzen
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
