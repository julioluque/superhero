# CONTAINER DEPLOYED OK -----------------------------------

# FROM openjdk:11-slim
# ADD target/superherodocker.jar superherodocker.jar
# EXPOSE 8087
# ENTRYPOINT ["java", "-jar", "superherodocker.jar"]


# NEW CONTAINER IN PROCESS-----------------------------------

FROM maven:3-openjdk-11-slim AS build

COPY . .
RUN mvn verify
#RUN mvn package -Dmaven.test.skip=true

FROM openjdk:11-slim

COPY --from=build target/superhero-0.0.1-SNAPSHOT.jar /

EXPOSE 8080
CMD java -jar /superhero-0.0.1-SNAPSHOT.jar

