#!/bin/bash

PURPLE='0;35'
NC='\033[0m' 
VERSAO=11
	
	echo  "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7) Olá Minerador! Eu sou Crypton, o seu assistente;"
	echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7) Digite seu nome por favor:"
	read name
	echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7) É um prazer tê-lo(a) conosco ${name}. Podemos começar? (S/N)" 
	read inicio
	if [ \"$inicio\" == \"n\" ]
	then
		echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7)  Tudo bem, até a próxima ${name}!"
		exit
	fi
		echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7) Vamos lá ${name}..."
		echo ""
		echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7)  Você deseja atualizar suas dependências? (S/N)?"
		read mensage
	if [ \"$mensage\" == \"n\" ]
	then
		echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7)  Tudo bem, não iremos atualiza-la!"
		echo ""
	fi	
		echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7) Ok, irei atualizar!"
		echo  "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7)  Instalando dependências..."
		echo -ne '\e[1;31m #####                     (33%)\r \e[0m'
		sleep 1
		echo -ne '\e[1;31m #############             (66%)\r \e[0m'
		sleep 1	
		echo -ne $(tput setaf 4)'#######################   (100%)\r'
		echo -ne '\n'
		sudo apt upgrade && sudo apt update	
		echo ""
		
		echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7)  Agora, vamos verificar o JAVA."

		echo  "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7)  Verificando..."
		echo -ne '\e[1;31m #####                     (33%)\r \e[0m'
		sleep 1
		echo -ne '\e[1;31m #############             (66%)\r \e[0m'
		sleep 1	
		echo -ne $(tput setaf 4)'#######################   (100%)\r'
		echo -ne '\n'
       
java -version
if [ $? -eq 0 ]
	then
		echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7) : Você já possui o java instalado!"
		echo ""
		echo "Nosso tempo juntos chegou ao fim, muito obrigado por confiar na CryptoVerse, espero ter sido útil! ${name}"
	else
		echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7)  Ops! Não encontrei nenhuma versão do Java instalado... "
		echo ""
		echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7) Mas fique tranquilo, posso resolver isso para você ${name}!"
		echo ""
		echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7) ${name}, realmente deseja instalar o JAVA em sua máquina? (S/N)"
	read inst
	if [ \"$inst\" == \"n\" ]
		then
			echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7)  Você optou por não instalar o Java, até a próxima ${name}!"
		else	
			echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7)  Ok! Vamos instalar o Java."
			echo ""
			echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7) Para continuar, precisamos seguir alguns passos, ok?"
			echo ""
			echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7) 1° passo - Adicionando o repositório!"
			sleep 2
			sudo add-apt-repository ppa:webupd8team/java -y
			clear
			echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7)  Está Atualizando... Quase lá."
			echo  "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7)  Verificando..."
			echo -ne '\e[1;31m #####                     (33%)\r \e[0m'
			sleep 1
			echo -ne '\e[1;31m #############             (66%)\r \e[0m'
			sleep 1	
			echo -ne $(tput setaf 4)'#######################   (100%)\r'
			echo -ne '\n'
			sleep 2
			sudo apt update -y
			clear
			
			if [ $VERSAO -eq 11 ]
				then
					echo "2° passo - Preparando a instalação da versão 11 do Java. "
					echo ""
					echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7) ${name} Quando for solicitado, confirme a instalação do Java."
					echo  "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7)  Verificando..."
					echo -ne '\e[1;31m #####                     (33%)\r \e[0m'
					sleep 1
					echo -ne '\e[1;31m #############             (66%)\r \e[0m'
					sleep 1	
					echo -ne $(tput setaf 4)'#######################   (100%)\r'
					echo -ne '\n'
					sudo apt install default-jre ; apt install openjdk-11-jre-headless; -y
					clear
					echo "$(tput setaf 4)[CrytoVerse]:$(tput setaf 7) Java instalado com sucesso!"
					echo ""
					echo "Nosso tempo juntos chegou ao fim, muito obrigado por confiar na CryptoVerse, espero ter sido útil! ${name}"
					echo ""
				fi	
	fi
fi

# ===================================================================
# Todos direitos reservados para o autor: Dra. Profa. Marise Miranda.
# Sob licença Creative Commons @2020
# Podera modificar e reproduzir para uso pessoal.
# Proibida a comercialização e a exclusão da autoria.
# ===================================================================
