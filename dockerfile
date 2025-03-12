# 1. Base Image
FROM openjdk:17-jdk-slim

# 2. Arbeitsverzeichnis erstellen
WORKDIR /app

# 3. Projektdateien kopieren
COPY . .

# 4. Build mit Gradle (Tests werden übersprungen)
RUN chmod +x gradlew
RUN ./gradlew build -x test --no-daemon

# 5. Umbenennen der JAR-Datei, falls notwendig
RUN mv build/libs/shopping-cart-0.0.1-SNAPSHOT.jar build/libs/shopping-cart.jar

# 6. JAR Datei ausführen
CMD ["java", "-jar", "build/libs/shopping-cart.jar"]
