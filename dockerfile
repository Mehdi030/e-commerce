# Base Image
FROM openjdk:17-jdk-slim

# Arbeitsverzeichnis setzen
WORKDIR /app

# Projektdateien kopieren
COPY build/libs/shopping-cart.jar app.jar

# Port für Spring Boot freigeben
EXPOSE 8080

# Startbefehl korrigieren
CMD ["java", "-jar", "app.jar"]
