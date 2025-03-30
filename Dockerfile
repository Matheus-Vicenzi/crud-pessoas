FROM ubuntu:20.04

RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    openjdk-11-jdk \
    maven

ENV WILDFLY_VERSION 27.0.1.Final
ENV WILDFLY_PATH /opt/jboss/wildfly
ARG PACKAGE_NAME jsf-primefaces-app-1.0-SNAPSHOT.war

RUN mkdir -p ${WILDFLY_PATH}

RUN wget https://github.com/wildfly/wildfly/releases/download/${WILDFLY_VERSION}/wildfly-${WILDFLY_VERSION}.tar.gz -P /tmp

RUN tar -xvf /tmp/wildfly-${WILDFLY_VERSION}.tar.gz -C ${WILDFLY_PATH}

RUN rm /tmp/wildfly-${WILDFLY_VERSION}.tar.gz

COPY standalone.xml ${WILDFLY_PATH}/wildfly-${WILDFLY_VERSION}/standalone/configuration/

RUN mkdir -p ${WILDFLY_PATH}/wildfly-${WILDFLY_VERSION}/modules/system/layers/base/org/postgresql/main

COPY module.xml ${WILDFLY_PATH}/wildfly-${WILDFLY_VERSION}/modules/system/layers/base/org/postgresql/main/

RUN wget https://jdbc.postgresql.org/download/postgresql-42.7.3.jar -P ${WILDFLY_PATH}/wildfly-${WILDFLY_VERSION}/modules/system/layers/base/org/postgresql/main/

WORKDIR /app
COPY . .

RUN mvn clean install -DskipTests

RUN mv /app/target/${PACKAGE_NAME} ${WILDFLY_PATH}/wildfly-${WILDFLY_VERSION}/standalone/deployments

WORKDIR ${WILDFLY_PATH}/wildfly-${WILDFLY_VERSION}

EXPOSE 8080

CMD ["bin/standalone.sh", "-b", "0.0.0.0"]
