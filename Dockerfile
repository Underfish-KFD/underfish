FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/underfish-app.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
