FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/oauth-service-*.jar /opt/app.jar
EXPOSE 8081
ENTRYPOINT exec java -jar /opt/app.jar
