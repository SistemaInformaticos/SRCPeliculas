
package Modelo.KNN;

import Modelo.KNN.MasCercanos;
import Modelo.Similitudes.Similitud;
import Modelo.SistemaRecomendacion;
import Modelo.SistemaRecomendacion;
import Modelo.Usuario;
import Modelo.Usuario;
import Persistencia.Excepciones.ErrorConexionBD;
import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jose Manuel
 */
public class AlgoritmoKNN {
    
    public List<MasCercanos> calculaKNN (int k, String funcion) throws ErrorConexionBD {
        Similitud s = new Similitud();
        List<MasCercanos> kVecinosUsu = new ArrayList();
        SistemaRecomendacion src=new SistemaRecomendacion();
        List<Usuario> usuarios = src.getUsuarios();
        Iterator u = usuarios.iterator();
        while (u.hasNext()) { //Para cada usuario buscamos los mas similares a él
            //Usuario a comparar
            Usuario user = (Usuario)u.next();
            ListaKSimilares usuSimilares = new ListaKSimilares (k);
            Iterator iter = usuarios.iterator ();
            while (iter.hasNext()) { //Para cada usuario, comparamos con el resto
                Usuario usuAux = (Usuario)u.next();
                if (!user.getNombre().equals(usuAux.getNombre())) {//Si el usuario no es el mismo
                    //Creamos la similitud
                    double valor=-1;
                    if (funcion.equals("Pearson")) {//Si se ha escogido pearson
                        valor = s.verSimilitudPearson(user, usuAux);
                    }
                    if (funcion.equals("Coseno")) {//Si se ha escogido coseno
                        valor = s.SimilaridadPear(user, usuAux);
                    }
                    Similitud simil = new Similitud (user,usuAux,valor); //Creamos la similitud
                    //Insertamos el elemento en la posicion indicada de la lista
                    usuSimilares.insertaVecino(simil);
                }
            }
            //Ahora agregamos el usuario con sus similares a la lista
            MasCercanos dato = new MasCercanos (user, usuSimilares);
            kVecinosUsu.add(dato);
        }
        return kVecinosUsu;
    }
 
}
