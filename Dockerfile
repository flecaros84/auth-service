# ============================
# 1) Build Stage (Maven Build)
# ============================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar el archivo pom.xml y resolver dependencias
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar el JAR
RUN mvn clean package -DskipTests


# ============================
# 2) Run Stage (Ligero y seguro)
# ============================
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
EXPOSE 8080

# Copiar JAR desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
