FROM openjdk:17-ea-slim-buster

WORKDIR /app

ADD target/*.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]