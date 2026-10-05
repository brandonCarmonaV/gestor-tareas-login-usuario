# Gestor de Tareas - Login Usuario

Backend para la autenticación y gestión de usuarios del sistema de tareas. Este microservicio permite registrar usuarios, iniciar sesión, manejar tokens JWT y administrar información básica del perfil del usuario.

## Descripción general

La aplicación está desarrollada con Java 21 y Spring Boot, siguiendo una arquitectura hexagonal con separación clara entre:

- Capa de dominio
- Casos de uso (application service)
- Adaptadores de entrada/salida
- Infraestructura de persistencia y seguridad

Incluye autenticación por correo y contraseña, encriptación de contraseñas con BCrypt, generación de tokens JWT y persistencia con PostgreSQL.

## Tecnologías

- Java 21
- Spring Boot 4.0.8
- Spring Web
- Spring Data JPA
- Spring Security Crypto
- PostgreSQL
- JWT (jjwt)
- Maven
- Docker

## Características

- Registro de usuarios
- Inicio de sesión con validación por credenciales
- Generación de access token y refresh token
- Manejo de cookies seguras para sesión
- Consulta del usuario autenticado por token
- CRUD básico de usuarios
- Encriptación de contraseñas con BCrypt
- Persistencia en base de datos relacional

## Estructura del proyecto

```text
gestor-tareas-login-usuario/
├── README.md
├── login-usuario/
│   ├── dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   └── target/
└── .github/
```

## Requisitos previos

Antes de ejecutar el proyecto asegúrate de tener instalado:

- Java 21
- Maven 3.9+
- PostgreSQL (o una base de datos compatible configurada en variables de entorno)
- Docker opcional para ejecución en contenedor

## Configuración

El servicio usa variables de entorno para la conexión a la base de datos y los valores JWT. Puedes definirlas antes de iniciar la aplicación:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/gestor_tareas
export DB_USERNAME=postgres
export DB_PASSWORD=tu_password
```

También puedes ajustar la configuración en:

```text
login-usuario/src/main/resources/application.properties
```

Configuraciones principales:

- `server.port=8090`
- `spring.datasource.*`
- `jwt.secret`
- `jwt.access-expiration-ms`
- `jwt.refresh-expiration-ms`

## Ejecución local

Desde la carpeta del proyecto `login-usuario`:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

La API quedará disponible en:

```text
http://localhost:8090
```

## Ejecución con Docker

```bash
docker build -t login-usuario ./login-usuario
docker run -p 8090:8090 --env DB_URL=jdbc:postgresql://host.docker.internal:5432/gestor_tareas --env DB_USERNAME=postgres --env DB_PASSWORD=tu_password login-usuario
```

## Endpoints principales

### Autenticación

- `POST /signup` - crea un usuario nuevo y realiza login automático
- `POST /login` - inicia sesión con email y contraseña
- `GET /logout` - elimina las cookies de sesión
- `GET /auth` - valida el usuario autenticado mediante la cookie `access_token`
- `GET /extract?token={jwt}` - extrae el sujeto autenticado desde un token

### Usuarios

- `POST /api/users` - crea un usuario
- `GET /api/users` - obtiene todos los usuarios
- `GET /api/users/{id}` - obtiene un usuario por ID
- `PATCH /api/users` - actualiza el usuario autenticado
- `DELETE /api/users` - elimina el usuario autenticado

## Ejemplo de login

```bash
curl -X POST http://localhost:8090/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@ejemplo.com",
    "pass": "miPassword123"
  }'
```

## Pruebas

```bash
cd login-usuario
./mvnw test
```

## Arquitectura

El proyecto utiliza una estructura basada en ports and adapters (puertos y adaptadores):

- `application/ports/input` - contratos de casos de uso
- `application/service` - implementación del negocio
- `domain` - modelos y excepciones del dominio
- `infrastructure/adapters/input/rest` - controladores REST
- `infrastructure/adapters/output/persistence` - acceso a datos
- `infrastructure/config` - configuración de Spring

## Notas

- La autenticación se realiza con JWT y cookies HTTP-only.
- El acceso a la API se gestiona mediante middleware y configuración de seguridad.
- El servicio está preparado para conectarse a PostgreSQL en entorno real y usar H2 en pruebas.

## Licencia

Este proyecto no define una licencia específica en el archivo de configuración de Maven. Si vas a reutilizarlo en un entorno profesional, valida la licencia antes de distribuirlo.

## Autor / mantenedor

Proyecto desarrollado para el módulo de autenticación y usuarios del sistema de gestión de tareas.
