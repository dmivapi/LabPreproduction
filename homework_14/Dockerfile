FROM adoptopenjdk/openjdk8

ENV APP_JAR_NAME="Dockerized-0.0.1-SNAPSHOT.jar"

WORKDIR /opt
COPY target/$APP_JAR_NAME $APP_JAR_NAME
COPY wait-for-it.sh wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["./wait-for-it.sh", "db:3306", "--", "java", "-jar", "Dockerized-0.0.1-SNAPSHOT.jar"]