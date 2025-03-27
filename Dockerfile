# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-21 AS build
WORKDIR /app
COPY pom.xml .  
RUN mvn dependency:go-offline  # Pre-download dependencies for faster builds
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create a minimal runtime image
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
