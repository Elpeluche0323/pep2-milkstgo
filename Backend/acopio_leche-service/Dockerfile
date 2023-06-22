FROM openjdk:20
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} acopio_leche-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/acopio_leche-service-0.0.1-SNAPSHOT.jar"]