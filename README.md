# Accenture Test - Avaliação Técnica para Desenvolvedor Full Stack

Este documento descreve os procedimentos necessários para configurar e executar um sistema full stack desenvolvido como parte de um processo seletivo da Accenture para a vaga de desenvolvedor.

## Estrutura do Projeto

A aplicação está dividida em duas camadas principais:

- **Serviço de Backend**: baseado em **Java**, utilizando o **Spring Boot**.
- **Interface do Usuário (Frontend)**: construída com o framework **Vue.js**.

---

## Tecnologias e Ferramentas Utilizadas

| Camada | Ferramenta/Tecnologia | Versão |
| :--- | :--- | :--- |
| Banco de Dados | PostgreSQL | 16.3 |
| Backend | Java | 17 |
| | Spring Boot | 3.5.5 |
| | Maven | 4.x |
| | Swagger (OpenAPI) | |
| Frontend | Vue.js | 3 |
| | Vite | 7.1.3 |
| | Vue Router | 4.5.1 |
| | Tailwind CSS | 4.1 |
| | PrimeVue | 4 |
| | Node.js | 22.15.0 |

---

## Como Executar o Projeto

### **Método 1: Usando Docker**

Essa abordagem permite iniciar o sistema rapidamente, com o mínimo de configuração local.

**Dependências Necessárias**:

- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

**Procedimento**

1.  Faça o clone do repositório:
    ```bash
    git clone https://github.com/daniel21gr/accenture-fulltstack-test.git
    cd accenture-fulltstack-test
    ```

2.  Construa e inicie os serviços definidos no Docker Compose:
    ```bash
    docker-compose up --build
    ```

3. Para encerrar a aplicação e limpar os containers, use `Ctrl + C` no terminal e execute `docker-compose down`.

### **Método 2: Instalação Manual**

Essa opção requer configuração do ambiente na máquina local.

**Requisitos**

* **[Git](https://git-scm.com/book/pt-br/v2/Começando-Instalando-o-Git)**
* **[Java Development Kit (JDK17)](https://www.oracle.com/java/technologies/javase/jdk17-0-13-later-archive-downloads.html)** (v.17)
* **[Node.js](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)** (v.22.15.0) e **[NPM](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)** (v.10.9.2)
* **[Apache Maven](https://maven.apache.org/install.html)** (v.3.9.8)
* **[PostgreSQL](https://www.postgresql.org/download)** (v.16.3)

**Etapas de Configuração do Ambiente:**

1.  Obter o código fonte
    ```bash
    git clone https://github.com/daniel21gr/accenture-fulltstack-test.git
    cd accenture-fulltstack-test
    ```

2.  Preparar o banco de dados
    * Inicie o serviço do PostgreSQL em sua máquina.
    * Crie uma base de dados para a aplicação:
        ```SQL
        CREATE DATABASE accenture_test;
        ```

3.  Ajustar as configurações do backend
    * Acesse o diretório `backend` do projeto.
    * VVerifique e, se necessário, edite o arquivo application.properties com as credenciais e porta corretas para seu banco.

---

## **Execução Manual da Aplicação**

### **Backend**
No terminal, acesse o diretório `backend` e execute:
  ```bash
  # Instalar dependências e compilar
  mvn clean install

  # Inicie a aplicação
  mvn spring-boot:run
  ```

### **Inicie o Frontend:**
Abra um novo terminal no diretório `frontend` para executar:
  ```bash
  # Instalar dependências
  npm install

  # Iniciar 'servidor
  npm run dev
  ```

---

## **Endpoints e Interface**

Após a inicialização completa, os serviços estarão disponíveis nos seguintes endereços locais:

* **API Backend:** [http://localhost:8080](http://localhost:8080/)
* **Documentação Swagger:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui.html)
* **Frontend:** [http://localhost:5173](http://localhost:5173/)

---

## Considerações Finais

Este projeto serve como referência técnica para avaliar conhecimentos em desenvolvimento web moderno utilizando ferramentas amplamente adotadas no mercado. A estrutura modular facilita a manutenção e permite escalar facilmente os componentes.

## Estrutura de Pastas

Organização de diretórios essenciais das camadas da aplicação:

```css
accenture-fulltstack-test/
├── backend/
│   ├── src/
│   │   ├── java/
│   │   └── resourcer/application.properties
│   └─ pom.xml
├── frontend/
│   ├── src/
│   └── package.json
└── docker-compose.yml
```
