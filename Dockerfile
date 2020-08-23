FROM openjdk:8-jdk-alpine
ADD target/boot-docker-*.jar /app.jar
ENTRYPOINT ["sh","-c","java -jar /app.jar"]