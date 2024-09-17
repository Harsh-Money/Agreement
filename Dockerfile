# Stage 1: Build the JAR file
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY . /app
RUN gradle clean build -x test # Skipping tests during the build

# Stage 2: Create a lightweight final image
FROM openjdk:21
WORKDIR /app
COPY --from=build /app/build/libs/agreement-0.0.1-SNAPSHOT.jar /app/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]

