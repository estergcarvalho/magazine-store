# Magazine Store

Projeto de e-commerce desenvolvido com o intuito de aprimorar habilidades de desenvolvimento.

---

## Requisitos

O projeto faz uso de: 
`Spring Boot 3.2.2`
`JDK 17`
`Maven 3.9.6`

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Flywal](https://flywaydb.org/)
- [Lombok](https://start.spring.io/)
- [PostgreSQL](https://www.postgresql.org/)
- [H2 database](https://www.h2database.com/html/main.html)

---

## Banco de dados da aplicação

`PostgreSQL`
  - URL: jdbc:postgresql://localhost:5432/magazinestore
  - usuario: magazinestore
  - senha: magazinestore

---

## Banco de dados para testes integrados

`H2`
  - URL: jdbc:h2:mem:testdb
  - Usuário: sa

---

## Instalação

### Clone o repositório

```bash
git clone <git@github.com:estergcarvalho/magazinestore.git>

```

### Navegue para o diretório do projeto

```bash
cd magazinestore
```

### Compilar projeto
```bash
mvn clean install
```

---

## Executar projeto

### Navegue para o diretório do projeto

```bash
cd magazinestore
```

### Execute o JAR

```bash
java -jar target/magazinestore-0.0.1-SNAPSHOT.jar
```
---

## Testes

### Navegue para o diretório do projeto 

### Execute os testes

```bash
mvn verify
```

---

## Documentação

http://localhost:8080/swagger-ui/index.html
