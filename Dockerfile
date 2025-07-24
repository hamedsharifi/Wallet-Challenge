FROM amazoncorretto:21.0.5-alpine3.19
ENV TZ=Asia/Tehran
WORKDIR /app
COPY infrastructure/target/infrastructure-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Duser.timezone=Asia/Tehran", "-jar", "app.jar"]
