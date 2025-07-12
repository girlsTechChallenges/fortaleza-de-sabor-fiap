# Estágio 1: Estágio de construção
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código fonte e constrói a aplicação
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Estágio de execução
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o JAR construído do estágio de build
COPY --from=build /app/target/fortaleza.sabor-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Executa a aplicação
CMD ["java", "-jar", "app.jar"]
