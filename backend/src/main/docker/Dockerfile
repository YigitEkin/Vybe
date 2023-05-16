FROM amazoncorretto:17-alpine-jdk
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]