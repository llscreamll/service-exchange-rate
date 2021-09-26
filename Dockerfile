FROM openjdk:8-jdk-alpine
LABEL maintainer="skate_max@mail.ru"
VOLUME /tmp
EXPOSE 8099
ARG JAR_FILE=build/libs/ex-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} v1.jar
ENTRYPOINT ["java","-jar","v1.jar"]
