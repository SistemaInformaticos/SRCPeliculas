/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oskar
 */
public class Preferencias{
    private static final String url="build/classes/Modelo/fichero.properties";
    private static Properties prop;
    
    static{
        try {
            (prop=new Properties()).load(new FileInputStream(url));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Preferencias.class.getName()+" fichero.properties no encontrado").log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Preferencias.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
    public static String leePreferencia(String nombre){
        return prop.getProperty(nombre);
    }
    public static void anadePreferencia(String nombre,String valor){
        prop.setProperty(nombre,valor);
        try {
            FileOutputStream os=new FileOutputStream(url);
            prop.store(os, "Fichero de Configuracion");
        } catch (IOException ex) {
            Logger.getLogger(Preferencias.class.getName()+"no se han podido guardar las preferencias").log(Level.SEVERE, null, ex);
        }
    }
    public static int numeroPreferencias(){
        return prop.size();
    }
    public static Map<String,String> getPreferencias(){
        Map<String,String> prefe = new HashMap();
        for (Enumeration e = prop.keys(); e.hasMoreElements(); ) {		 
          Object obj = e.nextElement();
           prefe.put((String)obj, prop.getProperty(obj.toString()));
        }
        return prefe;
    }
    
    @Override
    protected void finalize(){
        try {
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Preferencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
