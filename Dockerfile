# ---- Build stage ----
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven && \
    mvn -P docker package -DskipTests --no-transfer-progress

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=builder /build/target/underfish-app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
