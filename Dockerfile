FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# copy the project and build it
COPY pom.xml .
COPY src/ src
RUN mvn -B --no-transfer-progress package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]