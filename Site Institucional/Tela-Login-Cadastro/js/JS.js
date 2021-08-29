/* INICIO DO CÓDIGO QUE BUSCA OS ELEMENTOS "SIGNIN, SIGNUP, body" NO HTML */
var btnSignin = document.querySelector("#signin");
var btnSignup = document.querySelector("#signup");

var body = document.querySelector("body");
/* FINAL DO CÓDIGO QUE BUSCA OS ELEMENTOS "SIGNIN, SIGNUP, body" NO HTML */


/* INICIO DO CÓDIGO QUE É A FUNÇÃO QUE INICIA O MOVIMENTO DE TRANSIÇÃO. REPAREM NAS ULTIMAS LINHA DOS CÓDIGOS DE CSS. */
btnSignin.addEventListener("click", function(){
    body. className = "sign-in-js";
})


btnSignup.addEventListener("click", function(){
    body. className = "sign-up-js";
})
/* FINAL DO CÓDIGO QUE É A FUNÇÃO QUE INICIA O MOVIMENTO DE TRANSIÇÃO. REPAREM NAS ULTIMAS LINHA DOS CÓDIGOS DE CSS. */

