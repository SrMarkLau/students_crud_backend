# Etapa de construção
FROM gradle:7.5.0-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Etapa de execução
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]