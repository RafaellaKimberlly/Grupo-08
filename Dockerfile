FROM openjdk:11

WORKDIR /cryptoverse

RUN cd /Grupo-08

COPY /Backend/projeto-cryptoverse/target/projeto-cryptoverse-1.0-SNAPSHOT-jar-with-dependencies.jar /cryptoverse/

ENTRYPOINT java -jar /cryptoverse/projeto-cryptoverse-1.0-SNAPSHOT-jar-with-dependencies.jar

