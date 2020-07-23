FROM openjdk:8-jdk-alpine
ADD target/boot-docker-1.0.jar boot-docker-1.0.jar
ENTRYPOINT ["sh","-c","java -jar /boot-docker-1.0.jar"]