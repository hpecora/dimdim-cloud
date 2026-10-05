# DimDim

Projeto desenvolvido para a disciplina **DevOps Tools & Cloud Computing**, com o objetivo de aplicar conceitos de desenvolvimento Web, banco de dados PaaS, deploy em nuvem e monitoramento no Microsoft Azure.

## Sobre a solução

O DimDim é uma aplicação Web para controle simples de receitas e despesas.

A aplicação permite o gerenciamento de:

- Categorias
- Transações financeiras

As duas entidades possuem relacionamento entre si e operações completas de CRUD:

- Create
- Read
- Update
- Delete

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- Maven
- Azure SQL Database
- Azure App Service
- Azure CLI
- Application Insights
- Git e GitHub

## Arquitetura

```mermaid
flowchart LR
    U[Usuário / Navegador] --> A[Azure App Service]
    A --> S[Aplicação Spring Boot]
    S --> D[Azure SQL Database]
    A --> I[Application Insights]
    G[GitHub] --> A