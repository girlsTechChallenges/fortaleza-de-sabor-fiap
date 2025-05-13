FROM openjdk:21

COPY target/fortaleza.sabor-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
