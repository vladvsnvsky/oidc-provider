# Comment
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu
ARG JAR_FILE=build/*.jar
COPY build/libs/auth-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]