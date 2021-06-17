# Superhero

[![Repo](https://img.shields.io/badge/Repo-GitHub-blue)](https://github.com/julioluque/superhero)
[![Linkedin](https://img.shields.io/badge/Linkedin-Julio-blue)](https://www.linkedin.com/in/julio-luque/)

Api de superHeroes

## Status: In Process

**Te recomendamos usar [jdk-11][].**


### Sobre este proyecto

La api esta preparada para hacer consultas muy veloces ya que cuenta con un sistema de cacheo bascio y control de tiempo de procesos.
Se puede ejecutar con `Maven` y `Docker` y cuenta con un dockerfile en base a `openjdk-11-slim`. Cuenta con seguridad basica y tiene lo minimo necesario para ser funcional.  

[jdk11]: https://www.oracle.com/ar/java/technologies/javase-jdk11-downloads.html
* [Sobre este proyecto](#sobre-este-proyecto)
* [Proposito](#proposito)
* [Scripts DDL](#scripts-DDL)
* [@CustomRequestTimed](#customRequestTimed)
* [Excepciones](#excepciones)
* [Tests](#tests)
  * [Unitarios](#unitarios)
  * [De Integracion](#de-Integracion)
* [Docker](#docker)
  * [Dockerfile](#dockerfile)
* [Cache](#cache)
* [Documentacion](#documentacion)
* [Seguridad](#seguridad)
* [Releases](#releases)
* [Bugs](#bugs)

## Proposito

El proyecto de superheroes nace en la necesidad de crear un sistema que permita el mantenimiento dinamico de un listado de viajeros en los que puedan hacer busquedas de destinos y experienccias con una buena performance.


## Scripts DDL

• Utilizar alguna librería que facilite el mantenimiento de los scripts DDL de base de datos.

## @CustomRequestTimed

• Implementar una anotación personalizada que sirva para medir cuánto tarda en ejecutarse.
una petición. Se podría anotar alguno o todos los métodos de la API con esa anotación.
Funcionaría de forma similar al @Timed de Spring, pero imprimiendo la duración en un log.

## Excepciones

• Gestión centralizada de excepciones.

## Tests

• Test de integración.
    
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.meanbean</groupId>
      <artifactId>meanbean</artifactId>
      <version>2.0.3</version>
      <scope>test</scope>
    </dependency>

### Unitarios
### De Integracion
## Docker

• Presentar la aplicación dockerizada.

### Dockerfile
## Cache

• Poder cachear peticiones.

## Documentacion

• Documentación de la API.

```xml
<plugin>
  <groupId>com.spotify</groupId>
  <artifactId>docker-maven-plugin</artifactId>
  <configuration>
    <imageName>registry.example.com/my-image</imageName>
    ...
```

    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.9.2</version>
    </dependency>
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>2.9.2</version>
    </dependency>

## Seguridad

• Seguridad del API.

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

## Releases
## Bugs

Describir algunos errores

```
[ERROR] Failed to execute goal com.spotify:docker-maven-plugin:0.0.21:build (default) on project <....>: 
Exception caught: system properties: docker has type STRING rather than OBJECT
```

otra forma de descripcion

> Caused by: com.spotify.docker.client.shaded.javax.ws.rs.InternalServerErrorException: HTTP 500 Internal Server Error

ultima descripcion

#### Invalid repository name ... only [a-z0-9-\_.] are allowed
