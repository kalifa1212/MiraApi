FROM maven:3.8.5-openjdk-17 As build
COPY . .
RUN mvn clean package -DskipeTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/ModuleUser-0.0.1-SNAPSHOT.jar ModuleUser.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","ModuleUser.jar" ]