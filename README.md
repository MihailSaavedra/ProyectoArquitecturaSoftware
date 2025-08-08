# Proyecto Final: Arquitectura de Microservicios para la Gestión Integral de Publicaciones

Este proyecto es la entrega final para la materia de **Arquitectura de Software** en la Universidad de las Fuerzas Armadas ESPE.

- **Autores:** Mihail Saavedra - Ronny Paguay - Jossbell Cruz - Eloy Munoz
- **Docente:** Geovanny Cudco
- **Fecha de Entrega:** 8 de agosto de 2025

---
## 1. Descripción del Proyecto
El objetivo de este proyecto es implementar una plataforma distribuida para la gestión completa del ciclo de vida de publicaciones académicas. La arquitectura se basa en el paradigma de **microservicios desacoplados** para garantizar alta disponibilidad, escalabilidad y resiliencia.

El sistema permite el registro y autenticación de usuarios, la creación y gestión de publicaciones, y un flujo de aprobación que culmina con la notificación de eventos clave mediante comunicación asíncrona.

---
## 2. Diagrama de Arquitectura
El siguiente diagrama ilustra el flujo de comunicación entre los diferentes microservicios que componen el sistema:

[Diagrama de Arquitectura de Microservicios](diagrama-arquitectura.jpeg)

---
## 3. Tecnologías Utilizadas
- **Lenguaje:** Java 21
- **Framework:** Spring Boot 3
- **Arquitectura:**
    - **Service Discovery:** Spring Cloud Netflix Eureka
    - **API Gateway:** Spring Cloud Gateway
    - **Seguridad:** Spring Security (con autenticación basada en JWT)
- **Comunicación:**
    - **Síncrona:** RESTful APIs
    - **Asíncrona:** RabbitMQ como Broker de Mensajería
- **Base de Datos:** H2 Database (para entorno de desarrollo)
- **Contenerización:** Docker (para RabbitMQ)
- **Build Tool:** Apache Maven

---
## 4. Prerrequisitos
Para ejecutar este proyecto localmente, necesitarás tener instalado:
- JDK 21 o superior.
- Apache Maven.
- Docker Desktop (y debe estar en ejecución).

---
## 5. Instrucciones para Ejecutar Localmente

Sigue estos pasos en orden para levantar todo el ecosistema de microservicios.

**1. Clonar el Repositorio:**
bash
git clone [https://github.com/MihailSaavedra/ProyectoArquitecturaSoftware.git](https://github.com/MihailSaavedra/ProyectoArquitecturaSoftware.git)
cd ProyectoArquitecturaSoftware
2. Iniciar RabbitMQ:
Abre una terminal y ejecuta el siguiente comando de Docker:

Terminal dentro del proyecto, y a su vez iniciar docker
docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:3-management
3. Ejecutar los Microservicios:
Abre una terminal separada para cada microservicio, navega a su carpeta correspondiente y ejecuta el siguiente comando:

Bash

# Ejemplo para eureka-service (repetir para los demás)
cd eureka-service
./mvnw spring-boot:run
El orden de inicio recomendado es:

1. eureka-service
2. auth-service
3. publicaciones-service
4. notificaciones-service
5. gateway-service

Una vez iniciados, puedes verificar el estado de los servicios en el panel de Eureka: http://localhost:8761.

**6. Flujo de Prueba Básico (API Endpoints)**
Todas las peticiones se realizan al puerto del API Gateway (http://localhost:8080).

1. Registrar un Usuario:

POST /auth/register

Body: { "email": "usuario@test.com", "password": "123", "rol": "ROLE_AUTOR" }

2. Iniciar Sesión (Obtener Token):

POST /auth/login

Body: { "email": "usuario@test.com", "password": "123" }

3. Crear una Publicación (Requiere Token):

POST /api/publicaciones

Authorization Header: Bearer <token_jwt>

Body: { "titulo": "Mi Nuevo Artículo", "resumen": "Contenido..." }

4. Aprobar una Publicación (Requiere Token):

POST /api/publicaciones/{id}/approve

Authorization Header: Bearer <token_jwt>
