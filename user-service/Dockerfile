# Step 1: Base image (Java)
FROM eclipse-temurin:17-jdk-jammy

# Step 2: Copy jar from target folder into container
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Step 3: Run the app
ENTRYPOINT ["java", "-jar", "/app.jar"]