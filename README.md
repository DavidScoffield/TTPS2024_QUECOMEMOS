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
docker-compose up
```

> Esto levantara una base de datos postgres en el puerto `5432`, con el usuario `root` y contraseña `password`. Estos datos se pueden modificar en el archivo `docker-compose.yml`.

### Instalacion de dependencias

```bash
mvn install
```

## Ejecución de TEST

```bash
mvn test
```
