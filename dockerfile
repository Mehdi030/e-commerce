# 1. Base Image
FROM openjdk:17-jdk-slim

# 2. Arbeitsverzeichnis erstellen
WORKDIR /app

# 3. Projektdateien kopieren
COPY . .

# 4. Build mit Gradle
RUN chmod +x gradlew
RUN ./gradlew build --no-daemon

# 5. JAR Datei ausführen
CMD ["java", "-jar", "build/libs/shopping-cart.jar"]
