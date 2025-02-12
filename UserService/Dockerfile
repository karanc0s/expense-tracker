FROM openjdk:17-jdk-slim
LABEL authors="karandeep-singh"

ADD target/UserService.jar user-service.jar
ENTRYPOINT ["java", "-jar" , "user-service.jar"]