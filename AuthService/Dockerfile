FROM openjdk:17-jdk-slim
LABEL authors="karandeep-singh"

ADD target/AuthService.jar auth-service.jar
ENTRYPOINT ["java", "-jar" , "auth-service.jar"]