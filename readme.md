# Seventh

Este projeto é um teste técnico para desenvolvedor Java Full Stack na Seventh, implementando uma API RESTful para o gerenciamento de livros em uma biblioteca.

## Funcionalidades
- Gerenciamento de livros (CRUD completo: criar, listar, atualizar e excluir).
- Validação de dados de entrada com DTOs.
- Integração com o banco de dados MySQL utilizando Spring Data JPA.
- Migrações automatizadas de banco de dados com Flyway.
- Documentação automática da API com OpenAPI/Swagger UI.
- Cobertura com testes utilizando Mockito e integração com banco H2.


## Arquitetura do Projeto

O projeto adota o padrão MVC (Model-View-Controller) estruturado da seguinte forma:

- **Controller**: Contém os endpoints REST para acessar os recursos.
- **Service**: Implementa a lógica de negócios.
- **Repository**: Responsável pela interação com o banco de dados.
- **Model**: Representa as entidades do banco de dados.
- **DTO**: Objetos que transferem os dados de entrada.
- **Migration**: Scripts SQL mantidos em `src/main/resources/db/migration`.

### Exemplos de Endpoints

| Verbo HTTP | Endpoint              | Descrição                             |
|------------|-----------------------|---------------------------------------|
| `POST`     | `/biblioteca/livros`  | Adicionar um novo livro.              |
| `GET`      | `/biblioteca/livros`  | Listar todos os livros cadastrados.   |
| `GET`      | `/biblioteca/livros/titulo/{titulo}` | Buscar livro por título.        |
| `PUT`      | `/biblioteca/livros/{id}` | Atualizar um livro existente.        |
| `DELETE`   | `/biblioteca/livros/{id}` | Remover um livro pelo ID.             |

## Como Configurar e Rodar o Projeto

1. **Clonar o repositório**:
   ```bash
   git clone <url_do_repositorio>
   cd seventh
   ```

2. **Certifique-se de que o Docker está instalado**:
   O Docker Compose será utilizado para configurar e iniciar automaticamente um container com o banco de dados MySQL.

3. **Iniciar o Ambiente**:
   Utilize o comando abaixo para subir os serviços do projeto:
   ```bash
   docker-compose up
   ```

4. **Compilar e Rodar o Projeto**:
   Use o Maven para compilar e rodar o projeto:
   ```bash
   mvn spring-boot:run
   ```

5. **Acessar a Documentação da API**:
   Após a inicialização, a documentação interativa estará disponível em:
   ```
   http://localhost:8080/swagger-ui.html
   ```
   
6. **Testes**:
   Para rodar os testes, utilize o comando:
   ```bash
   mvn test
   ```

## Scripts de Migração do Flyway

Os scripts de banco de dados estão localizados em `src/main/resources/db/migration`.

## Requisitos do Sistema
- **Java 21**: Certifique-se de ter o JDK 21 instalado.
- **Docker**: Necessário para gerenciamento do banco de dados.

---
**Autor:** João Vitor da Silva  
