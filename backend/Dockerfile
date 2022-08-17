# syntax=docker/dockerfile:1

FROM openjdk:11-jre
COPY ./backend/build/libs/app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=./server-manage/application-real.properties"]