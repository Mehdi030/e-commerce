# 1. Base Image
FROM openjdk:17-jdk-slim AS build

# 2. Arbeitsverzeichnis setzen
WORKDIR /app

# 3. Alle Projektdateien kopieren
COPY . .

# 4. Gradle Wrapper ausführbar machen und Build starten
RUN chmod +x gradlew && ./gradlew build -x test --no-daemon

# 5. Neues Image für die App
FROM openjdk:17-jdk-slim

WORKDIR /app

# 6. JAR-Datei aus dem Build-Container kopieren
COPY --from=build /app/build/libs/shopping-cart.jar app.jar

# 7. Port für Spring Boot freigeben
EXPOSE 8080

# 8. Startbefehl setzen
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
