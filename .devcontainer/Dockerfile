# Para entorno de desarrollo 

#Imagen base de Java

FROM eclipse-temurin:21.0.5_11-jdk

# Establecer el directorio de trabajo para el contenedor
WORKDIR /app

# Copiar archivos necesarios para Gradle
COPY ./gradle ./gradle
COPY ./gradlew ./gradlew
COPY ./build.gradle.kts ./build.gradle.kts
COPY ./settings.gradle.kts ./settings.gradle.kts

# Dar permisos al wrapper de Gradle
RUN chmod +x ./gradlew

# Descargar dependencias de Gradle
RUN ./gradlew dependencies --no-daemon

# Copiar el código fuente
COPY ./src ./src

# Exponer el puerto 8080
EXPOSE 8080

# Comando predeterminado para el desarrollo
CMD ["./gradlew", "bootRun", "--no-daemon"]
