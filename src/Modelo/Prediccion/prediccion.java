/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Prediccion;

import Modelo.Pelicula;
import Modelo.SistemaRecomendacion;
import Modelo.Usuario;
import Persistencia.Excepciones.ErrorConexionBD;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import Modelo.*;
import similitud.Similitud;

/**
 *
 * @author Usuario
 */
public class prediccion {
    HashMap p = new HashMap();
    Similitud sim = new Similitud();
    SistemaRecomendacion s = new SistemaRecomendacion();
    //Factor de normalizacion el inverso de la suma de las similitudes de un usuario ("Necesitamos lista de usuarios registrados")
    private double factorNormalizacion (Usuario u) throws ErrorConexionBD{
        double toRet=0.0;
        Usuario aux;
        SistemaRecomendacion s = new SistemaRecomendacion();
        aux = s.getUsuarios().get(u.getId()); // esto tendra que ser un get dentro del hashmap de usuarios  dentro de SRC
        s.eliminarUsuario(u.getId()); // esto tendra que ser un remove dentro del hashmap de usuarios dentro de SRC
        Iterator it =s.getUsuarios() s.getUsuarios().entrySet().iterator();
        while(it.hasNext()){
            toRet = toRet + sim.similaridadCos(u, it.next()); //se escoge el tipo de algoritmo de similitud lo suyo seria pasarlo por parametro en la clase principal.
        }
        return 1/toRet;
    }
    public double WeightedSum(Usuario u, Pelicula p){
        //PeliculaValorada pv;
        double aux=0.0;
        double auxsim =0.0;
        Usuario usuarioaux;
        LinkedList <Usuario> usuCon = new LinkedList();
        usuCon=(LinkedList<Usuario>) s.getUsuariosPelicula(p);
        Iterator it = usuCon.iterator();
        while (it.hasNext()){
            usuarioaux= (Usuario) it.next();
            auxsim = auxsim + sim.similaridadCos(u, usuarioaux);
            aux = aux + (sim.similaridadCos(u, usuarioaux) * usuarioaux.getValoracion(p.getId()));
        }
        return aux/auxsim;
    }
    public double WeightedSumAverage(Usuario u, Pelicula p){
        double aux=0.0;
        double auxsim =0.0;
        Usuario usuarioaux;
        LinkedList <Usuario> usuCon = new LinkedList();
        usuCon=(LinkedList<Usuario>) s.getUsuariosPelicula(p);
        Iterator it = usuCon.iterator();
        while (it.hasNext()){
            usuarioaux= (Usuario) it.next();
            auxsim = auxsim + sim.similaridadCos(u, usuarioaux);
            aux = aux + (sim.similaridadCos(u, usuarioaux) * (usuarioaux.getValoracion(p.getID())-usuarioaux.getValoracionMediaUsuario()));
        }
        return u.getValoracionMediaUsuario()+(aux/auxsim);
    }
}

