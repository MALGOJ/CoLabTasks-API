# CoLabTasks-API
Aplicacion de tareas colaborativas backend 

## Descripción del proyecto

Este proyecto es una API de gestión de tareas colaborativas diseñada para ayudar a los usuarios a gestionar proyectos, tareas, notificaciones y recordatorios. Proporciona un servicio backend para crear, actualizar, recuperar y eliminar varias entidades relacionadas con la gestión de proyectos.

## Cómo funciona

La API permite a los usuarios:
- Gestionar proyectos y tareas.
- Asignar tareas a usuarios.
- Establecer recordatorios y notificaciones.
- Registrar y gestionar cuentas de usuario.

## Características principales

- Gestión de proyectos**: Crear, actualizar, recuperar y eliminar proyectos.
- Gestión de tareas**: Crear, actualizar, recuperar y eliminar tareas asociadas a proyectos.
- Gestión de usuarios**: Registro de nuevos usuarios, actualización de datos de usuario y eliminación de usuarios.
- Notificaciones**: Gestión de notificaciones para diversos eventos.
- Recordatorios**: Establecer y gestionar recordatorios para tareas.

## Tecnologías y herramientas utilizadas

- Lenguajes**: Kotlin, Java
- Marcos de trabajo**: Spring Boot
- Herramienta de construcción**: Gradle
- Base de datos**: PostgreSQL (para producción)
- Pruebas**: JUnit 5, Mockito

## Descripción del Backend

El backend está construido usando Spring Boot y sigue una arquitectura orientada a servicios. Incluye varios servicios para manejar diferentes aspectos de la aplicación, como gestión de proyectos, gestión de tareas, gestión de usuarios, notificaciones y recordatorios. Cada servicio interactúa con la base de datos a través de repositorios y utiliza mapeadores para convertir entre entidades y DTOs.

## Librerías utilizadas


- Spring Boot Starter Web**: Para construir aplicaciones web.
- Spring Boot Starter Data JPA**: Para interacciones con bases de datos.
- Spring Boot Starter Security**: Para asegurar la aplicación.
- Spring Boot Starter Test**: Para probar la aplicación.
- **Mockito**: Para simular dependencias en pruebas unitarias.
- JUnit 5**: Para escribir y ejecutar pruebas unitarias.

## Información sobre las pruebas unitarias

Las pruebas unitarias se escriben utilizando JUnit 5 y Mockito. Las pruebas cubren varios aspectos de la aplicación, incluyendo métodos de servicio e interacciones con el repositorio. Cada servicio tiene una clase de prueba correspondiente que verifica la funcionalidad de sus métodos. Las pruebas aseguran que la aplicación se comporta como se espera y maneja los casos extremos de manera apropiada.

## Posibles mejoras y nuevas funcionalidades 

# Mejoras
1.Manejador centralizado de excepciones
2.Validación de datos más robusta
3.Optimización del modelo relacional
4 Pruebas más exhaustivas incremento del coverage

# nuevas funcionalidades 
1.Sistema avanzado de permisos y roles
2.Historial de cambios en tareas y proyectos
3.Subtareas dentro de tareas
4.Integración con calendarios externos
5.Reportes y análisis de productividad
6.Búsquedas avanzadas
7.Soporte para archivos adjuntos

## Porcentaje de IA utilizado en el proyecto 50%
