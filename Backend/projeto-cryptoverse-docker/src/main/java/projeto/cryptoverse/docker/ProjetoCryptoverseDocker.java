package projeto.cryptoverse.docker;


import projeto.cryptoverse.docker.AcoesCli;




public class ProjetoCryptoverseDocker {

    public static void main(String[] args) {
        


        AcoesCli sistema = new AcoesCli();

        System.out.println("\nFavor Insirir seu Login\n");

        sistema.loginCli();
        
        sistema.leiturasRamCli();

    }
    
    
    
}
