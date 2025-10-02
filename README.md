

![Status: Concluído](https://img.shields.io/badge/status-concluído-green)
![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?logo=spring)
![Oracle DB](https://img.shields.io/badge/Oracle-Database-red?logo=oracle)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Frontend-green?logo=thymeleaf)


---

## 🧑‍💻 Autores

<div align="center">

| Nome | RM |
| :--- | :--- |
| **Vinicius Murtinho Vicente** | 551151 |
| **Lucas Barreto Consentino** | 557107 |
| **Gustavo Bispo Cordeiro** | 558515 |

</div>





## ✨ Funcionalidades Principais

Autenticação & Autorização com Spring Security.

 CRUD Completo para:
Filiais
Motos
Usuários
Validações de Negócio:
Capacidade máxima de pátios.
Campos únicos (placa, chassi, usuário).
Dashboard Interativo com métricas e gráficos.
Busca Dinâmica por placa, chassi ou usuário.

---

## 🏗️ Arquitetura do Sistema

A aplicação segue uma arquitetura em camadas (Layered Architecture).

Frontend (Thymeleaf, JS)
      |
Controller Layer (API Endpoints)
      |
Service Layer (Business Logic)
      |
Repository Layer (Spring Data JPA)
      |
Database (Oracle)


---

## 🛡️ Detalhes de Segurança

* Autenticação: Login com senhas encriptadas em BCrypt.

* Autorização: Perfis ROLE_ADMIN e ROLE_USER.

* Proteção CSRF para formulários.

* Controle de acesso por filial (usuários USER só acessam dados da própria filial).

---

## 🛠️ Tecnologias Utilizadas

#### **Backend**
* **Java 17**
* **Spring Boot 3.x**
* **Spring Web (MVC)**
* **Spring Data JPA**
* **Spring Security**
* **Lombok**

#### **Frontend**
* **Thymeleaf**

#### **Banco de Dados & Ferramentas**
* **Oracle Database**
* **Flyway** 
* **Maven** 

---

## 🚀 Como Executar Localmente

### **Pré-requisitos**
* Java JDK 17 ou superior
* Uma instância do Oracle Database acessível
* Git


---




