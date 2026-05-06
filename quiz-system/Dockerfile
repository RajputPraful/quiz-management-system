# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk-alpine
# This wildcard ensures it finds the jar even if the name changes
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]