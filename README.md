## Cadastro de Pessoas

Sobre o Projeto

Este projeto é um sistema web para cadastro de pessoas, desenvolvido como parte de um desafio técnico. A aplicação 
permite realizar operações CRUD (Create, Read, Update, Delete) através 

A implementação segue as diretrizes do desafio, utilizando Java Server Faces (JSF) com PrimeFaces, um banco de dados relacional (PostgreSQL) e JPA (Hibernate). Além disso, o projeto utiliza EJB para injeção de dependências.

### Tecnologias Utilizadas

JSF (Java Server Faces): Framework para desenvolvimento de aplicações web Java.

PrimeFaces: Biblioteca de componentes para JSF, proporcionando uma interface rica e interativa.

PostgreSQL: Sistema de gerenciamento de banco de dados relacional.

Hibernate: Implementação de JPA para mapeamento objeto-relacional.

EJB (Enterprise JavaBeans): Utilizado para injeção de dependências.

Maven: Gerenciador de dependências e build automation.

Docker: Conteinerização da aplicação para facilitar a execução.

JUnit e Mockito: Frameworks para testes automatizados.

### Estrutura do Projeto

A aplicação segue uma estrutura MVC (Model-View-Controller) para separação de responsabilidades:

Model: Contém as entidades JPA e lógica de acesso ao banco de dados.

View: Implementada com JSF e PrimeFaces.

Controller: Contém os Managed Beans que fazem a ponte entre a camada de visualização e a lógica de negócios.

### Como Executar o Projeto

Para rodar o projeto localmente, siga os passos abaixo:

##### 1. Clonar o repositório

git clone https://github.com/Matheus-Vicenzi/kumulus-test.git
cd kumulus-test

##### 2. Construir a aplicação

mvn clean install

##### 3. Subir os containers Docker

Certifique-se de que o Docker está instalado e em execução. Em seguida, execute:

docker-compose up -d

Isso iniciará os seguintes serviços:

PostgreSQL: Banco de dados

JBoss: Servidor de aplicação para rodar a aplicação JSF

##### Acessar a aplicação

Após iniciar os contêineres, a aplicação estará disponível em:

http://localhost:8080/seu-contexto

### Como Executar os Testes

Para rodar os testes automatizados, utilize o seguinte comando:

mvn test