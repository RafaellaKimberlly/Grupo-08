/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjetoCryptoverse;

import com.github.britooo.looca.api.core.Looca;

/**
 *
 * @author imkbe
 */
public class ExtraiDados {
        
    Looca looca = new Looca();
    
    ConnectionBD connection = new ConnectionBD();
    
    public Double getCpuUso(){
    Double cpuUso = looca.getProcessador().getUso().doubleValue();
    return cpuUso;
    }
    
    public Double getDisco(){
    Double disco = looca.getGrupoDeDiscos().getTamanhoTotal().doubleValue()/1000000000;
    return disco;
    }
    
    public Double getMemoriaEmUso() {
    Double memoriaemUso = looca.getMemoria().getEmUso().doubleValue() /1000000000;
    return memoriaemUso;
    }    

    String getProcessador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
