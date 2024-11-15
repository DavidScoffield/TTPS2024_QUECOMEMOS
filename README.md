# QUECOMEMOS

> Proyecto de la asignatura TTPS - Curso 2024 - **Grupo 6**

## Integrantes

- Scoffield David
- Lovera Paco

## Requerimientos

- Java 17
- Maven 3.6.3 (utilizado)
- Docker, y Docker compose (opcional, se usa para levantar la base de datos)

## Instalacion

### Base de datos

Para levantar la base de datos, se puede utilizar docker-compose, ejecutando el siguiente comando en la raiz del proyecto:

```bash
docker compose up
```

> Esto levantara una base de datos postgres en el puerto `5432`, con el usuario `root` y contraseña `password`. Estos datos se pueden modificar en el archivo `docker-compose.yml`.

### Instalacion de dependencias

```bash
mvn install
```

## Levantar ambiente de desarrollo

```bash
mvn spring-boot:run
```

## Ejecución de TEST

```bash
mvn test
```

## Documentación API

### Postman

Puede acceder a los endpoints de prueba en el archivo TTPS-QUECOMEMOS.postman_collection.json. Importe este archivo en Postman para acceder a los mismos.

### Swagger

La documentación de la API se encuentra en la ruta `/swagger-ui.html`.

### Archivo

También puede acceder a la documentación de la API en formato json en la ruta `/api-docs`.
