# Gym API

API REST para la gestión de usuarios, autenticación y rutinas de gimnasio. Incluye funcionalidades de registro y login de usuarios locales, integración con Google OAuth, generación de tokens JWT para autenticación segura, y manejo global de excepciones.

## Tecnologías

- Java 17+
- Spring Boot
- Maven
- Spring Security (para autenticación JWT)
- Spring Data JPA (para persistencia)
- Librerías de Google (para validación de tokens OAuth)

## Estructura del proyecto

```
src/
main/
java/
com/
scaffold/
template/
controllers/          # Controladores REST (AuthController, DummyController)
dtos/                 # Objetos de transferencia de datos (LoginResponseDto, RegisterRequestDto, etc.)
entities/             # Entidades JPA (UserEntity, DummyEntity)
models/               # Modelos de dominio (User, AuthProvider, etc.)
repositories/         # Repositorios JPA (UserRepository, DummyRepository)
services/             # Servicios de negocio (AuthService, DummyService)
impl/                 # Implementaciones de servicios (AuthServiceImpl, CustomUserDetailsService)
config/               # Configuraciones (GlobalExceptionHandler, JwtAuthenticationFilter, etc.)
resources/
application.properties  # Configuración de la aplicación
test/
java/
com/
scaffold/
template/             # Pruebas unitarias
```

## Configuración

1. Clona el repositorio.
2. Configura las variables en `src/main/resources/application.properties` (ej. base de datos, claves JWT, credenciales de Google).
3. Ejecuta con Maven:

```
mvn spring-boot:run
```

## Funcionalidades principales

- **Autenticación de usuarios**: Registro y login con credenciales locales o Google OAuth.
- **Generación de JWT**: Tokens seguros para sesiones de usuario.
- **Manejo de excepciones**: Respuestas JSON estandarizadas para errores (400 Bad Request con mensaje personalizado).
- **Gestión de usuarios**: Creación, búsqueda y validación de usuarios.
- **Endpoints de prueba**: DummyController para pruebas básicas.

## Endpoints principales

### Autenticación
- `POST /auth/register` — Registro de usuario local (requiere email, password, etc.).
- `POST /auth/registerWithGoogle` — Registro con Google OAuth (requiere tokenId).
- `POST /auth/login` — Login de usuario local (devuelve JWT si exitoso).
- `POST /auth/google-login` — Login con Google OAuth (devuelve JWT si el usuario existe).

### Otros
- `GET /dummy` — Endpoint de prueba (requiere autenticación JWT).

Todos los endpoints de autenticación devuelven respuestas en JSON. Errores se manejan globalmente con códigos 400 y mensajes personalizados.

## Pruebas

Ejecuta las pruebas con:

```
mvn test
```

## Autor

Proyecto inicial generado por Martín Zea (mzeacardenas@gmail.com).