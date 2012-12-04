/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package srcpeliculas;

import Modelo.SistemaRecomendacion;
import Persistencia.Excepciones.ErrorConexionBD;

/**
 *
 * @author admin
 */
public class SRCPeliculas {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws ErrorConexionBD {
        // TODO code application logic here
        SistemaRecomendacion SRC = new SistemaRecomendacion();
        if(SRC.validarDatosUsuario("usuario43@mail.com")){
            System.out.println("disponible"+"adadsasd");
        }
    }
}
