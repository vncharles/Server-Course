FROM openjdk:17-jdk-alpine

COPY target/server-0.0.1-SNAPSHOT.jar server-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", "-jar", "/server-0.0.1-SNAPSHOT.jar" ]