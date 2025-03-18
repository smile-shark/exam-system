FROM openjdk:22-jdk-slim
WORKDIR /app
copy ./docker-project/exam-system-server-0.0.1-SNAPSHOT.jar /app/exam-system-server-0.0.1-SNAPSHOT.jar
expose 8080
entrypoint ["java", "-jar", "exam-system-server-0.0.1-SNAPSHOT.jar"]