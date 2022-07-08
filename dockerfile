FROM openjdk:11-jdk

ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} smart-home.jar
ENTRYPOINT ["java","-Dspring.config.location=classpath:/application.yml","-jar", "/smart-home.jar"]