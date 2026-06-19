# Cadastro de Ninjas API

API REST desenvolvida com Spring Boot para gerenciamento de Ninjas e Missões, com autenticação JWT, controle de acesso baseado em perfis (ADMIN e USER), documentação Swagger e cobertura de testes unitários e de integração.

---

## Arquitetura da Solução

![Arquitetura](docs/arquitetura.drawio.png)

### Camadas

- **Presentation**: Controllers REST e endpoints da API
- **Application**: Services e regras de negócio
- **Domain**: Entidades, exceções e regras do domínio
- **Infra**: Repositórios, segurança, mappers e configurações

---

## Casos de Uso

![Use Case Diagram](docs/diagrama_api.png)

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- SQL Server
- Swagger/OpenAPI
- JUnit 5
- Mockito
- MockMvc
- Maven
- Docker
- Docker Compose
---

## 🐋 Containerização

A aplicação foi containerizada utilizando **Docker** e **Docker Compose** para garantir um ambiente de execução isolado, seguro e de fácil replicação.

### 📄 Dockerfile
Responsável por gerar uma imagem otimizada da API Spring Boot contendo:

* **Java 17** como ambiente de execução (Runtime).
* Aplicação empacotada em formato **JAR**.
* Exposição da **porta 8080** para comunicação externa.

### 🛠️ Docker Compose
Responsável por gerenciar o ciclo de vida do container, encarregando-se de:

* Construir a imagem da aplicação automaticamente.
* Configurar as variáveis de ambiente necessárias para a API.
* Executar a API em um ambiente containerizado robusto.

---

## Funcionalidades

### Usuários

- Cadastro de usuários
- Autenticação via JWT
- Controle de acesso por perfil

### Ninjas

- Criar ninja
- Buscar ninja por ID
- Listar ninjas
- Atualizar ninja
- Remover ninja
- Buscar ninja por nome
- Buscar ninja por trecho do nome

### Missões

- Criar missão
- Atualizar missão
- Remover missão
- Listar missões

### Relacionamentos

- Associação de ninjas a missões

---

## Segurança

A aplicação utiliza autenticação baseada em JWT.

### Perfis disponíveis

- ROLE_ADMIN
- ROLE_USER

### Permissões

| Operação | ADMIN | USER |
|-----------|---------|---------|
| GET Ninjas | ✅ | ✅ |
| POST Ninjas | ✅ | ❌ |
| PUT Ninjas | ✅ | ❌ |
| DELETE Ninjas | ✅ | ❌ |
| GET Missões | ✅ | ✅ |
| POST Missões | ✅ | ❌ |
| PUT Missões | ✅ | ❌ |
| DELETE Missões | ✅ | ❌ |

---

## Testes

### Testes Unitários

Cobertura das regras de negócio da camada Service utilizando:

- JUnit 5
- Mockito

### Testes de Integração

Validação dos endpoints REST utilizando:

- Spring Boot Test
- MockMvc
- JWT real da aplicação

---

## Documentação da API

A documentação está disponível através do Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```
---

## Como Executar

### Clonar o projeto

```bash
git clone https://github.com/DelclecianoSuporte/CadastroDeNinjas.git
```

### Entrar na pasta do projeto

```bash
cd CadastroDeNinjas
```

### Executar a aplicação

```bash
mvn spring-boot:run
```

## 🚀 Executando com Docker

Você pode rodar a aplicação utilizando o **Docker** de forma isolada ou através do **Docker Compose** para maior praticidade.

### 🐳 Utilizando Docker Puro

**1. Gerar a imagem da aplicação:**
```bash
docker build -t cadastro-ninjas-api .

---
