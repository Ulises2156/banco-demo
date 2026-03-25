**Sistema Bancario Backend (Spring Boot + PostgreSQL)**
Desarrollé una API REST completa para la gestión de cuentas bancarias, implementando operaciones como creación de cuentas, depósitos, retiros, transferencias y consulta de historial de transacciones.

**Tecnologías:** Java 21, Spring Boot, Spring Security, JWT, JPA/Hibernate, PostgreSQL, Swagger

**Funcionalidades principales:**

* Autenticación y autorización con JWT
* Sistema multiusuario con roles (ADMIN / USER)
* Control de acceso: usuarios solo ven sus cuentas
* Operaciones bancarias: depósitos, retiros y transferencias
* Registro de transacciones con historial por cuenta
* Validaciones y manejo de errores
* Documentación de API con Swagger

**Arquitectura:**

* Capas Controller / Service / Repository
* Uso de DTOs para desacoplar la lógica de negocio
* Persistencia con JPA/Hibernate


