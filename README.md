## Cadastro de Pessoas

Sobre o Projeto

Este projeto é um sistema web para cadastro de pessoas, desenvolvido como parte de um desafio técnico. A aplicação
permite realizar operações CRUD (Create, Read, Update, Delete) através de uma interface web intuitiva, bem como a
possibilidade de integração via API REST.

A implementação segue as diretrizes do desafio, utilizando Java Server Faces (JSF) com PrimeFaces, um banco de dados 
relacional (PostgreSQL) e JPA (Hibernate). Além disso, o projeto utiliza EJB para injeção de dependências.

### Tecnologias Utilizadas

- JSF (Java Server Faces): Framework para desenvolvimento de aplicações web Java.

- PrimeFaces: Biblioteca de componentes para JSF, proporcionando uma interface rica e interativa.

- PostgreSQL: Sistema de gerenciamento de banco de dados relacional.

- Hibernate: Implementação de JPA para mapeamento objeto-relacional.

- EJB (Enterprise JavaBeans): Utilizado para injeção de dependências.

- Maven: Gerenciador de dependências e build automation.

- Docker: Conteinerização da aplicação para facilitar a execução.

- JUnit e Mockito: Frameworks para testes automatizados.

- Jboss WildFly: Servidor de aplicação.

- JAX-RS: Utilizada a implementação RESTeasy, implementação padrão do WildFly

### Estrutura do Projeto

Explicação das estruturas de pastas e arquitetura:

- Adapter: Implementação do design pattern adapter, utilizado para desacoplar objetos de determinadas camadas.

- Beans: Beans utilizados pelo PrimeFaces para a renderização das páginas.

- Configuration: Classes de configuração do sistema.

- Controller: Acesso via requisição HTTP de recursos, utilizando atualmente o RESTeasy, que é a implementação padrão do
WildFly 27.0.1 para o JAX-RS. As configurações gerais podem ser definidas na classe 
`com.example.configuration.ApplicationPath`, atualmente padronizado como rota de acesso `/api/{recuso}`, buscando
utilizar o padrão REST.

- DTO: Classes utilizadas para transferir dados da camada de controle para a camada de aplicação.

- Enums: Definição de constantes.

- Exceptions: Aqui devem ser criadas as exceções personalizadas.  

- Manager: No contexto dessa aplicação, os Managers são responsáveis por fazer a integração com outros sistemas parceiros.

- Model: Contém as entidades JPA e entidades de domínio da aplicação.

- Repository: Classes que abstraem acessos ao banco de dados.

- Service: Regras de negócio relacionadas ao domínio relativo.

- Util: Classes de utilidade gerais.

Caso a classe a ser criada seja de um determinado contexto de domínio, deve ser criada com o pacote de nome 
`{camada}.{domínio}`, por exemplo, a controller de pessoas está no caminho: `com.exemplo.controller.pessoa.PessoaController`,
onde controller é a camada e pessoa é o domínio.

### Como Executar o Projeto

Para rodar o projeto localmente, siga os passos abaixo:

##### 1. Clonar o repositório
```sh
git clone https://github.com/Matheus-Vicenzi/kumulus-test.git
```
```sh
cd kumulus-test
```

##### 2. Subir os containers Docker

Certifique-se de que o Docker está instalado e em execução. Em seguida, execute:

docker compose up -d

Isso iniciará os seguintes serviços:

PostgreSQL: Banco de dados

JBoss: Servidor de aplicação para rodar a aplicação JSF

##### Acessar a aplicação

Após iniciar os contêineres, a aplicação estará disponível em:

http://localhost:8080/jsf-primefaces-app-1.0-SNAPSHOT

### Como Executar os Testes

Para rodar os testes automatizados, utilize o seguinte comando:

```sh
mvn test
```

### Configurações adicionais

##### Utilizando o Postgres

Para o gerenciamento automático do pool de conexões do PostgreSQL, é necessária a configuração do modulo do postgres no
WildFly, o arquivo `module.xml` e configurações na montagem da imagem através do `Dockerfile` são os responsáveis por 
isso. As configurações de acesso ao banco de dados, como rota e senha, podem ser encontradas no arquivo standalone.xml

##### Standalone.xml

O arquivo `standalone.xml` é responsável por diversas configurações do Jboss WildFly, como por exemplo:
- Configurações de logs
- Definição de rotas/senhas/usuario de acesso ao banco de dados
- Configuração de porta e host da aplicação, entre outros.

Na raiz do projeto, existe a versão modificada do `standalone.xml` com as alterações necessárias para a execução.
No momento da construção da imagem através do Dockerfile, o arquivo `standalone.xml` da raiz do projeto substitui o 
arquivo original do WildFly, definindo nossas preferências e configurações para a aplicação.

### Integrações

Atualmente, a aplicação consome a API gratuita do [ViaCep](https://viacep.com.br/) para consulta de CEPs.  
A integração está implementada na classe `com.example.manager.ViaCepManager`, 
que segue o contrato definido por `com.example.manager.CepManager`.

##### Implementações futuras

- Observabilidade com OpenTelemetry para exportação de métricas, logs e traces.
- Adição de filtros de servlets.
- Proteção de determinadas rotas através de autenticação JWT e autorização RBAC (Role-based access control).
- Estruturação e definição de padrões para o gitflow e gerenciamento de pull requests.
- Realizar a documentação das APIs com a especificação OpenApi.
- Adicionar e aprimorar os testes automatizado, focando em testes end-to-end e testes de integração.
