# CONTAINER DEPLOYED OK -----------------------------------

FROM openjdk:11-slim

ADD target/superherodocker.jar superherodocker.jar

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "superherodocker.jar"]