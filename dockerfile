# 1. Basis-Image mit Java 17
FROM openjdk:17-jdk-slim

# 2. Arbeitsverzeichnis im Container
WORKDIR /app

# 3. Kopiere nur die Build-Skripte (damit Caching funktioniert)
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

# 4. Installiere die Abhängigkeiten
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# 5. Kopiere nur den Quellcode (ohne "build" & ".gradle")
COPY src src

# 6. Starte die Anwendung mit DevTools
CMD ["./gradlew", "bootRun"]
