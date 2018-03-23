FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/gateway-service-*.jar /opt/app.jar
EXPOSE 8080
ENTRYPOINT exec java -jar /opt/app.jar
