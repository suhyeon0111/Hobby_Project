# syntax=docker/dockerfile:1

FROM openjdk:11-jre
COPY ./backend/build/libs/app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]