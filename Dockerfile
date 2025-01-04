# Etapa 1: Construcción
FROM eclipse-temurin:21.0.5_11-jdk AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar archivos necesarios
COPY ./gradle ./gradle
COPY ./gradlew ./gradlew
COPY ./build.gradle.kts ./build.gradle.kts
COPY ./settings.gradle.kts ./settings.gradle.kts
COPY ./src ./src

# Dar permisos al wrapper de Gradle
RUN chmod +x ./gradlew

# Compilar el proyecto y generar el JAR ejecutable
RUN ./gradlew clean bootJar --no-daemon

# Etapa 2: Ejecución
FROM eclipse-temurin:21.0.5_11-jre

# Establecer el directorio de trabajo
WORKDIR /api

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
