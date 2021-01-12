FROM adoptopenjdk/openjdk8

ENV APP_JAR_NAME="Dockerized-0.0.1-SNAPSHOT.jar"

WORKDIR /opt
COPY target/$APP_JAR_NAME $APP_JAR_NAME

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Dockerized-0.0.1-SNAPSHOT.jar"]