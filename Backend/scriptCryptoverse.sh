#!/bin/sh

inicializar_Api() {

	echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4): Inicializando API"

	echo ""

	cd projeto-cryptoverse-docker 

	echo ""

	echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4): Buildando imagem ou imagem em construção"

	sudo docker build -f Dockerfile -t cryptoverse-finalizado .

	echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4): API esta iniciando Aguarde por favor!"

	docker run -p 8085:8085 cryptoverse-finalizado

}

instalar_Api() { 

	if [ "$(ls -l | grep 'projeto-cryptoverse-docker.zip' | wc -l)" = '0' ]; then

		echo ""

		echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4): Baixando API, aguarde!"

		wget https://transfer.sh/get/d8hemH/projeto-cryptoverse-docker.zip 1>/dev/null 2>/dev/stdout

		echo ""

		echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4): Descompactando arquivo Jar"

		sudo unzip projeto-cryptoverse-docker.zip

		echo ""

		inicializar_Api

	else

		echo ""

		echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4): API já foi instalada"

		echo ""

		inicializar_Api

	fi

}

verificar_java() {

	echo ""

	echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4) Verificando se o java já foi instalado"

	if [ "$(dpkg --get-selections | grep 'default-jre' | wc -l)" = '0' ]; then

		echo ""

		echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4) Não há Java instalado"

		echo ""

		echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4) Iniciando instalação do Java"

		sudo apt install default-jre -y 1>/dev/null 2>/dev/stdout

		echo ""

		echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4) Java Instalado com sucesso!"

		instalar_Api

	else

		echo ""

		echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4) Você já possui Java instalado em sua máquina"

		instalar_Api

	fi

}

instalar_Docker() {

    echo ""

    echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4) Atualizando!"

    sleep 3

    sudo apt update && sudo apt upgrade -y 1>/dev/null 2>/dev/stdout

    echo ""

    echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4) Instalando docker <3"

    sleep 3

    sudo apt install docker.io 1>/dev/null 2>/dev/stdout

    echo ""

    echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4) Iniciando o docker <3"

    sleep 3

    sudo systemctl start docker

    echo ""

    echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4) Dando permissões para o docker <3"

    sleep 3

    sudo systemctl enable docker

    echo ""

    echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4) Verificando images no docker"

    sleep 3

    sudo docker images 

    echo ""

    echo "$(tput setaf 4)[Cryptoverse API Installer]:$(tput setaf 4) Docker instalado com sucesso!!!"

    sleep 3

    echo ""


    verificar_java

}

instalar_Docker