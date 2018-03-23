FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/eureka-service-*.jar /opt/app.jar
EXPOSE 8761
ENTRYPOINT exec java -jar /opt/app.jar
