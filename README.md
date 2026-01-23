# Gym API

API REST para la gestión de usuarios y rutinas de gimnasio.

## Tecnologías

- Java 17+
- Spring Boot
- Maven

## Estructura del proyecto

```
src/
main/
java/
com/
scaffold/
template/
controllers/
dtos/
entities/
models/
repositories/
services/
impl/
resources/
application.properties
test/
java/
com/
scaffold/
template/
```

## Configuración

1. Clona el repositorio.
2. Configura las variables en `src/main/resources/application.properties`.
3. Ejecuta con Maven:

```
bash
mvn spring-boot:run
```

## Endpoints principales

- `/auth/register` — Registro de usuario
- `/auth/registerWithGoogle` — Registro con Google
- `/dummy` — Endpoint de prueba

## Pruebas

Ejecuta las pruebas con:

```
bash
mvn test
```

## Autor

Proyecto inicial generado por Martín Zea (mzeacardenas@gmail.com).

```
