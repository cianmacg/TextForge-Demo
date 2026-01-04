FROM gradle:jdk25 AS build
WORKDIR /app

COPY --chown=gradle:gradle . .

RUN chmod +x gradlew

# Build the JAR file
RUN ./gradlew clean build -x test

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]