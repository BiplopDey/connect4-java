FROM openjdk:17-jdk-alpine as build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src

RUN ./mvnw package


FROM openjdk:17-jdk-alpine

EXPOSE 8080

COPY --from=build /app/target/*.jar /spring-petclinic.jar

CMD ["java", "-jar", "/spring-petclinic.jar"]