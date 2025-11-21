# PocketShop - Backend Services

PocketShop es una plataforma de comercio electrónico para la compra y venta de artículos, con un enfoque en la gestión de usuarios, productos, transacciones y comunicación entre compradores y vendedores.

## Estructura del Proyecto

El backend de PocketShop está compuesto por varios microservicios independientes que se comunican entre sí, cada uno con su propia base de datos MySQL. La arquitectura sigue un patrón de microservicios con las siguientes componentes principales:

### Servicios Principales

1. **User Service** (`users-service/`)
   - Gestión de usuarios (registro, autenticación, perfiles)
   - Control de accesos y permisos
   - Puerto: 8090

2. **Cards Service** (`cards-service/`)
   - Gestión de productos o artículos (tarjetas)
   - Búsqueda y filtrado de productos
   - Puerto: 8081

3. **Sales & Buy Service** (`sales-buy-services/`)
   - Procesamiento de transacciones de compra/venta
   - Gestión de carritos de compra
   - Puerto: 8082

4. **Notification Service** (`notification-service/`)
   - Notificaciones en tiempo real
   - Alertas y recordatorios
   - Puerto: 8083

5. **Chat Service** (`chat-service/`)
   - Comunicación entre usuarios
   - Mensajería en tiempo real
   - Puerto: 8085

### Base de Datos

- **MySQL 8.0** con múltiples bases de datos:
  - `pocketshop_users`: Datos de usuarios y autenticación
  - `pocketshop_cards`: Catálogo de productos/artículos
  - `pocketshop_buys`: Transacciones de compra/venta
  - `pocketshop_notification`: Notificaciones del sistema
  - `pocketshop_chat`: Mensajes y chats entre usuarios

## Requisitos Previos

- Docker y Docker Compose
- Java 17 o superior (para desarrollo local)
- Maven (para construcción local)

## Inicio Rápido

1. Clona el repositorio:
   ```bash
   git clone [URL_DEL_REPOSITORIO]
   cd ps-backend
   ```

2. Inicia los servicios con Docker Compose:
   ```bash
   docker-compose up --build
   ```
   Esto construirá e iniciará todos los servicios y la base de datos MySQL.

3. Accede a los servicios en:
   - User Service: http://localhost:8090
   - Cards Service: http://localhost:8081
   - Sales & Buy Service: http://localhost:8082
   - Notification Service: http://localhost:8083
   - Chat Service: http://localhost:8085

## Desarrollo

Cada servicio es un proyecto Spring Boot independiente con su propio `pom.xml`. Para desarrollar localmente:

1. Asegúrate de tener MySQL ejecutándose (puedes usar el contenedor Docker)
2. Configura las variables de entorno necesarias en cada servicio
3. Ejecuta cada servicio desde tu IDE o con Maven:
   ```bash
   cd [nombre-servicio]
   ./mvnw spring-boot:run
   ```

## API Documentation

Cada servicio incluye documentación de su API accesible en:
- `http://localhost:[PUERTO]/swagger-ui.html`
- `http://localhost:[PUERTO]/v3/api-docs`

## Licencia

Este proyecto está bajo la licencia [LICENSE].

## Contribución

Las contribuciones son bienvenidas. Por favor, lee nuestras pautas de contribución antes de enviar un pull request.

## Contacto

Para consultas, por favor contacta al equipo de desarrollo.