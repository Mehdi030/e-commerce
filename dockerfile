# Nutze das offizielle Java 17 Image
FROM openjdk:17-jdk-slim

# Setze das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere das gebaute JAR-File in den Container
COPY build/libs/*.jar app.jar

# Exponiere Port 8080 für den Webserver
EXPOSE 8080

# Starte die Spring Boot Anwendung
CMD ["java", "-jar", "app.jar"]
