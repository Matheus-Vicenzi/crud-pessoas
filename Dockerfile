# Use uma imagem base com Ubuntu
FROM ubuntu:20.04

# Instale dependências necessárias
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    openjdk-11-jdk \
    maven

# Defina variáveis para facilitar
ENV WILDFLY_VERSION 27.0.1.Final
ENV WILDFLY_PATH /opt/jboss/wildfly
ARG PACKAGE_NAME jsf-primefaces-app-1.0-SNAPSHOT.war

# Crie a pasta para o WildFly
RUN mkdir -p ${WILDFLY_PATH}

# Baixe o WildFly e extraia na pasta temporária
RUN wget https://github.com/wildfly/wildfly/releases/download/${WILDFLY_VERSION}/wildfly-${WILDFLY_VERSION}.tar.gz -P /tmp

# Extraia o arquivo para o diretório /opt/jboss
RUN tar -xvf /tmp/wildfly-${WILDFLY_VERSION}.tar.gz -C ${WILDFLY_PATH}

# Remova o arquivo tar após a extração
RUN rm /tmp/wildfly-${WILDFLY_VERSION}.tar.gz

# Substitua o arquivo standalone.xml com a versão personalizada
COPY standalone.xml ${WILDFLY_PATH}/wildfly-${WILDFLY_VERSION}/standalone/configuration/

# Crie a pasta para o módulo PostgreSQL
RUN mkdir -p ${WILDFLY_PATH}/wildfly-${WILDFLY_VERSION}/modules/system/layers/base/org/postgresql/main

# Copie o arquivo module.xml para o Wildfly
COPY module.xml ${WILDFLY_PATH}/wildfly-${WILDFLY_VERSION}/modules/system/layers/base/org/postgresql/main/

# Baixe o driver JDBC do PostgreSQL
RUN wget https://jdbc.postgresql.org/download/postgresql-42.7.3.jar -P ${WILDFLY_PATH}/wildfly-${WILDFLY_VERSION}/modules/system/layers/base/org/postgresql/main/

# Copie o código-fonte da aplicação para dentro do container
WORKDIR /app
COPY . .

# Compilar a aplicação com Maven
RUN mvn clean install -DskipTests

# Copie o arquivo WAR gerado para a pasta deployments do WildFly
RUN mv /app/target/${PACKAGE_NAME} ${WILDFLY_PATH}/wildfly-${WILDFLY_VERSION}/standalone/deployments

# Definir o diretório de trabalho para o WildFly
WORKDIR ${WILDFLY_PATH}/wildfly-${WILDFLY_VERSION}

# Expor a porta do WildFly
EXPOSE 8080

# Executar o WildFly
CMD ["bin/standalone.sh", "-b", "0.0.0.0"]
