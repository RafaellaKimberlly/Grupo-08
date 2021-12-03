FROM openjdk:11

WORKDIR /Grupo-08

RUN cd /Grupo-08

COPY /Backend/projeto-cryptoverse/target/projeto-cryptoverse-1.0-SNAPSHOT-jar-with-dependencies.jar /Grupo-08

ENTRYPOINT java -jar projeto-cryptoverse-1.0-SNAPSHOT-jar-with-dependencies.jar

