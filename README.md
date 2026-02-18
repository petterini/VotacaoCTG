# Votação CTG

Sistema web para gerenciamento de votação de CTG (Centro de Tradições
Gaúchas), permitindo cadastro de usuários, participantes e controle do
processo de votação.

------------------------------------------------------------------------

## Como começar a utilizar

Siga o passo a passo abaixo para configurar e executar o sistema
corretamente.

------------------------------------------------------------------------

## 1. Criar o Banco de Dados

No PostgreSQL, crie um banco de dados com o seguinte nome:

``` sql
CREATE DATABASE votacaoctg;
```

------------------------------------------------------------------------

## 2. Configurar o `application.properties`

No arquivo:

    src/main/resources/application.properties

Altere as configurações para os dados do seu banco:

``` properties
spring.datasource.url=jdbc:postgresql://localhost:5432/votacaoctg
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

Substitua `SEU_USUARIO` e `SUA_SENHA` pelos dados do seu PostgreSQL.

------------------------------------------------------------------------

## 3. Rodar o Projeto pela Primeira Vez

Execute a aplicação (via IDE ou terminal).

Na primeira execução, o Hibernate criará automaticamente as tabelas no
banco de dados, incluindo a tabela:

``` sql
usuario (
    id uuid not null,
    cpf varchar(255) not null,
    mesa bigint not null,
    roles varchar[],
    senha varchar(255),
    primary key (id)
)
```

------------------------------------------------------------------------

## 4. Inserir Usuário Administrador

Após a criação das tabelas, execute o seguinte `INSERT` no banco para
criar o primeiro usuário ADMIN.

-   CPF válido utilizado: **12345678909**
-   Senha: o mesmo CPF (criptografado com BCrypt strength 12)
-   Mesa: 1000
-   Role: ADMIN

``` sql
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

INSERT INTO usuario (id, cpf, mesa, roles, senha)
VALUES (
    gen_random_uuid(),
    '12345678909',
    1000,
    ARRAY['ADMIN'],
    '$2a$12$b6NRNO8wYOszbXkcSq46s.JYYDbz1IVEYPy418DKe.hnspslMooym'
);
```

------------------------------------------------------------------------

## 5. Acessar o Sistema

Com o usuário criado, acesse no navegador:

    http://localhost:8080/login

------------------------------------------------------------------------

## 6. Realizar Login

Utilize:

-   **Numero da mesa:** 1000
-   **Senha:** 12345678909

------------------------------------------------------------------------

## 7. Utilização do Sistema

Após o login como ADMIN, você poderá:

-   Cadastrar/Editar/Excluir usuários
-   Cadastrar/Editar/Excluir participantes
-   Gerenciar votações

------------------------------------------------------------------------

## Observações

-   Certifique-se de que o PostgreSQL esteja rodando antes de iniciar a
    aplicação.
-   O sistema utiliza Spring Boot + Hibernate + PostgreSQL.
-   A role `ADMIN` possui permissões administrativas completas.
