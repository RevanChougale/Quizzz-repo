FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/QuizService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 1111

ENTRYPOINT ["java","-jar","app.jar"]
