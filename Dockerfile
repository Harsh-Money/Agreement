## Build stage
FROM gradle:7.6-jdk21 AS build
WORKDIR /app
COPY . /app
RUN gradle clean build -x test # Skipping tests during the build

## Package stage
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/agreement-0.0.1-SNAPSHOT.jar /app/application.jar
EXPOSE 8080
CMD ["java", "-jar", "application.jar"]

