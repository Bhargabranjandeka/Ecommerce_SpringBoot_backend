# ======================
# Stage 1: Build with Maven + Java 23
# ======================
FROM maven:3.9.9-eclipse-temurin-23 AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the actual source code
COPY src ./src

# Package the application (skip tests for speed)
RUN mvn clean package -DskipTests

# ======================
# Stage 2: Run the application (Java 23 JDK runtime)
# ======================
FROM eclipse-temurin:23-jdk-alpine

WORKDIR /app

# Copy only the built jar from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
