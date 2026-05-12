# # Build stage
# FROM maven:3.9.0-eclipse-temurin-17 AS builder
# WORKDIR /build
# COPY pom.xml .
# RUN mvn dependency:go-offline
# COPY src ./src
# RUN mvn clean package -DskipTests

# # Runtime stage
# FROM eclipse-temurin:17-jre-alpine
# WORKDIR /app
# COPY --from=builder /build/target/*.jar app.jar

# EXPOSE 9090

# ENTRYPOINT ["java", "-jar", "app.jar"]
# CMD ["--spring.profiles.active=prod"]




# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

# copy pom first (cache dependencies)
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

# copy source
COPY src ./src

# build jar
RUN mvn clean package -DskipTests

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 9090
ENTRYPOINT ["java","-jar","app.jar"]