# build stage (optional for later); for now we’ll do a simple runtime image
FROM eclipse-temurin:21-jre

# app.jar is what Maven builds to target/
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]
