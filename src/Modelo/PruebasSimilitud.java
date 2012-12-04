/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Pruebas.Mejoras;
import Modelo.Pruebas.Metricas;
import Modelo.Pruebas.Prediccion;
import Modelo.Pruebas.Similitud;
        

/**
 *
 * @author Martinez
 */
public class PruebasSimilitud {
   
    public static int algoritmo(String nombreAlgoritmo){
        switch(nombreAlgoritmo){
            case "Similitud":                
//                Similitud.ejecutar();
                break;
            case "Prediccion":
                Prediccion.ejecutar();                
                break;
            case "Metricas":
                Metricas.ejecutar();
                break;
            case "Mejoras":
                Mejoras.ejecutar();
                break;                              
        }     
            
        return 0;
    }
    
}
