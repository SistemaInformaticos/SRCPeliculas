package Modelo;

/**
 * MD5
 * SHA1
 */

import Modelo.Excepciones.ErrorCodificacionPass;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author admin
 */
public class Pass {
    
    /**
    * Encripta un String.
    * @return El algoritmo encriptado
    * @param palabra
    */
    public static String encriptar(String palabra) throws ErrorCodificacionPass{
        try {
            return hash(palabra);
        } catch (NoSuchAlgorithmException ex) {
            throw new ErrorCodificacionPass();
        }
    }

    /**
    * Encripta un String.
    * @return String
    * @throws Exception
    */
    private static String hash(String clear) throws NoSuchAlgorithmException {
        
        MessageDigest md = MessageDigest.getInstance(Preferencias.leePreferencia("algoritmo"));
        byte[] b = md.digest(clear.getBytes());
        int size = b.length;
        StringBuilder h = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int u = b[i]&255;
            if (u<16){
                h.append("0").append(Integer.toHexString(u));
            }else{
                h.append(Integer.toHexString(u));
            }
        }
        return h.toString();
    }
    
    public void CambiaAlgoritmo(String valor) throws FileNotFoundException, IOException{
        Preferencias.anadePreferencia("algoritmo",valor);
    }
    
    
}