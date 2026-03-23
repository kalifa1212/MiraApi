# Étape 1 : build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : image finale
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/ModuleUser-0.0.1-SNAPSHOT.jar ModuleUser.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ModuleUser.jar"]

#FROM maven:3.8.5-openjdk-17 As build
#COPY . .
#RUN mvn clean package -DskipeTests
#
#FROM openjdk:17.0.1-jdk-slim
#COPY --from=build /target/ModuleUser-0.0.1-SNAPSHOT.jar ModuleUser.jar
#EXPOSE 8080
#ENTRYPOINT [ "java","-jar","ModuleUser.jar" ]