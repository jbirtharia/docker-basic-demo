FROM openjdk:8-jdk-alpine
ADD target/boot-docker-*.jar boot-docker-*.jar
ENTRYPOINT ["sh","-c","java -jar /boot-docker-*.jar"]