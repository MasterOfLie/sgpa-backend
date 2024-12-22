FROM maven:3.9.9-amazoncorretto-21-alpine AS build

LABEL authors="Matheus"

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src /app/src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/SGPA-0.0.1-SNAPSHOT.jar /app/sgpa.jar

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-jar", "/app/sgpa.jar"]