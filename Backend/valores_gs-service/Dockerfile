FROM openjdk:18
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} valores_gs-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/valores_gs-service-0.0.1-SNAPSHOT.jar"]