/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.KNN;

import java.util.Vector;

/**
 *
 * @author Jose Manuel
 */
public class ListaKSimilares {
    
    int numK;
    
    Vector<Similitud> vecinos;
    
    int posMin;
    float valorMin;
    
    /**Crea un vector de k vecinos mas cercanos*/
    public ListaKSimilares (int k) {
        
        numK = k;
        vecinos = new Vector ();
        posMin=-1;
        valorMin=-1;
    }
    
    /**Inserta un nuevo vecion*/
    boolean insertaVecino (Similitud simil) {
        
        boolean insertado = false;
        
        if (vecinos.isEmpty()) {//Si no hay ningun vecino insertado...
            
            vecinos.add(simil);
            posMin = 0;
            valorMin= simil.getSimilitud();
            
            insertado=true;
            
        } else if (vecinos.size()<numK) { //Si hay menos de k vecinos
            
            vecinos.add(simil);
            
            if (simil.getSimilitud()<valorMin) {//Si la similitud es menor que el minimo
                
                posMin = vecinos.size()-1;
                valorMin = simil.getSimilitud();
            }
            insertado=true;
            
        } else { //Si hay ya k vecinos, miramos si la valoracion es mayor que el minimo
            
            if (simil.getValoracion()>valorMin) {//Si es mayor que el minimo, la insertamos
                
                //Eliminamos el minimo actual
                vecinos.removeElementAt(posMin);
                
                //insertamos el nuevo dato
                vecinos.add(simil);
                
                //Vemos que elemento seria el minimo ahora
                posMin = vecinos.buscaPosMin();
                valorMin = vecinos.elementAt(posMin).getSimilitud();  
                insertado=true;
            }
        }
        
        return insertado;
    }
    
    private int buscaPosMin () {
        
        int pos=0;
        
        for (int i=1; i<vecinos.size(); i++) {
            
            if (vecinos.elementAt(i).getSimilitud()<vecinos.elementAt(pos).getSimilitud()) //Si la similitud es menor
                pos = i;
        }
        
        return pos;
    }
    
    
    
}
