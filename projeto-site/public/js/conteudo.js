function leitura_componente(idUsuario) {

    b_usuario.innerHTML = sessionStorage.nome_usuario_meuapp;

    idUsuario = Number(sessionStorage.getItem('id_usuario_meuapp'));

    console.log(idUsuario);

    fetch(`/leituras/situacao_componente/${idUsuario}`, {
        method: "GET"
    }).then(function (response) {
        if (response.ok) {
            response.json().then(function (resposta) {
                for (let i = 0; i < resposta.length; i++) {
                    var resp = resposta[i];
                    if (resp.nomeComponente == "Memoria-Ram") {
                        situacao_ram.innerHTML = resp.valor +"%";
                        console.log("RAM: " + resp.valor)
                    } else if (resp.nomeComponente == "cpu") {
                        situacao_cpu.innerHTML = resp.valor +"%";
                        console.log("CPU: " + resp.valor)
                    } else if (resp.nomeComponente == "Disco") {
                        console.log("Disco: " + resp.valor)
                        situacao_disco.innerHTML = resp.valor +"%";
                    } else {
                        console.log("Sem leitura");
                    }
                }
            });
        } else {
            console.log('Erro de login!');
        }
    });

    return false;
}


