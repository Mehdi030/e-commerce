# Use Java 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy everything from the project directory
COPY . .

# 🔥 Make sure `gradlew` is executable
RUN chmod +x gradlew

# Build the project inside the container
RUN ./gradlew build --no-daemon

# Copy the generated JAR file
COPY build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
