FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/car-stats-viewer-rest-api-*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]