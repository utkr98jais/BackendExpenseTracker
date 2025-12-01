# Multi-stage build for smaller production image
# ---- Build Stage ----
# Use official Maven with Temurin JDK 17 so we don't rely on mvnw/.mvn being present
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cache dependencies
COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline

# Copy source and build
COPY src/ src/
RUN mvn -q -DskipTests clean package

# ---- Runtime Stage ----
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Add non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Copy built jar from build stage
COPY --from=build /app/target/expenseManager-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render sets $PORT)
EXPOSE 8080

# JVM tuning for small memory (Render free instance ~512MB)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -XX:InitialRAMPercentage=50 -XX:+ExitOnOutOfMemoryError"

# Spring Boot will read PORT env automatically if server.port not hardcoded.
# So prefer not to hardcode server.port in properties when deploying.
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --server.port=$PORT"]
