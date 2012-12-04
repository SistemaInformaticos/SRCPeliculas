/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.KNN;


import Modelo.Similitudes.Similitud;
import Modelo.Usuario;
import Modelo.Usuario;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Jose Manuel
 */
public class MasCercanos {
    
    Usuario _usu;
    
    List<Similitud> _vecinos;
    
    
    public MasCercanos (Usuario u, List<Similitud> vecinos) {
        
        _usu = u;
        _vecinos = vecinos;
    }
    
}
