FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/projectForRC-1.0-SNAPSHOT.jar
WORKDIR /opt/app
EXPOSE 5432
COPY ${JAR_FILE} store.jar
ENTRYPOINT ["java","-jar","store.jar"]