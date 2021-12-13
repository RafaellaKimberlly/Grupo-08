FROM openjdk:11

WORKDIR /Grupo-08

RUN cd /Grupo-08

COPY /Backend/projeto-cryptoverse/target/projeto-cryptoverse.jar /Grupo-08

ENTRYPOINT java -jar projeto-cryptoverse.jar

