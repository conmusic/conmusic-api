FROM eclipse-temurin:17

WORKDIR /app

COPY target/conmusic-api-3.0.5.jar /app/conmusic-api-3.0.5.jar

ENTRYPOINT ["java", "-jar", "conmusic-api-3.0.5.jar"]