# Verwende ein Java 17 Image
FROM openjdk:17-jdk-slim

# Setze das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere das gesamte Projekt in den Container
COPY . .

# Baue das JAR-File innerhalb des Containers
RUN ./gradlew build --no-daemon

# Kopiere das erstellte JAR-File
COPY build/libs/*.jar app.jar

# Exponiere Port 8080
EXPOSE 8080

# Starte die Spring Boot Anwendung
CMD ["java", "-jar", "app.jar"]
