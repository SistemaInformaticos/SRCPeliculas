/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Similitudes;

import Modelo.PeliculaValorada;
import Modelo.Usuario;
import java.util.Iterator;
import java.util.LinkedList;
import modelo.*;

/**
 *
 * @author admin
 */
public class Similitud {
        LinkedList<PeliculaValorada> v1 = new LinkedList<PeliculaValorada> ();
        LinkedList<PeliculaValorada> v2 = new LinkedList<PeliculaValorada> ();
        
     private void obtenerMismasPeliculasValoradas(Usuario u1, Usuario u2){
                Iterator it = u1.getValoraciones().entrySet().iterator();
                //Recorremos las peliculas valoradas por el usuario1
                while (it.hasNext()){
                        PeliculaValorada p = (PeliculaValorada)it.next();
                        //Si el usuario 2 ha valorado la pelicula
                        if(u2.getValoraciones().contains(p.getTitulo().getkey())){
                                //Insertamos en el v1 la pelicula con la valoracion del usuario1
                                v1.add(p.getValue());
                                //Insertamos en el v2 la pelicula con la valoracion del usuario2
                                v2.add(u2.getValoraciones().get(p.getkey()));
                        }
                }
        }
     public Double similaridadCos(Usuario usu1, Usuario usu2){
          obtenerMismasPeliculasValoradas(usu1,usu2);
          if (v1.isEmpty()){
              return 0.0;
          }else{
            return (SumatorioproductoValoraciones(v1,v2))/(Math.sqrt(SumatorioValoracionesCuadrado(v1))*Math.sqrt(SumatorioValoracionesCuadrado(v2)));
          }
      }
     private double SumatorioproductoValoraciones (LinkedList<PeliculaValorada> a1, LinkedList<PeliculaValorada> a2){
         double toRet=0.0;
         int i=0;
         while(!a1.isEmpty()){
             toRet = toRet + a1.get(i).getPuntuacion() * a2.get(i).getPuntuacion();
             i++;
         }
         return toRet;
     }
     private double SumatorioValoracionesCuadrado(LinkedList<PeliculaValorada> pv){
         double toRet=0.0;
         Iterator it = pv.iterator();
         while (it.hasNext()){
             toRet = toRet + Math.pow(it.next().getPuntuacion(),2);
         }
         return toRet;
     }
     public double SimilaridadPear(Usuario u1, Usuario u2){
        obtenerMismasPeliculasValoradas(u1, u2);
        double sum_sq_x=0; double sum_sq_y=0; double sum_coproduct = 0;
        double media_x = v1.get(0).getPuntuacion(); double media_y = v2.get(0).getPuntuacion();
        for(int i=2;i<v1.size();i++){
            double sweep =Double.valueOf(i-1)/i;
            double valoracionMenosMedia_x = v1.get(i-1).getPuntuacion()-media_x;
            double valoracionMenosMedia_y = v2.get(i-1).getPuntuacion()-media_y;
            sum_sq_x += valoracionMenosMedia_x * valoracionMenosMedia_x * sweep;
            sum_sq_y += valoracionMenosMedia_y * valoracionMenosMedia_y * sweep;
            sum_coproduct += valoracionMenosMedia_x * valoracionMenosMedia_y * sweep;
            media_x += valoracionMenosMedia_x / i;
            media_y += valoracionMenosMedia_y / i;
        }
        double pop_sd_x = (double) Math.sqrt(sum_sq_x/v1.size());
        double pop_sd_y = (double) Math.sqrt(sum_sq_y/v1.size());
        double cov_x_y = sum_coproduct / v1.size();
        double  result;
        result = cov_x_y / (pop_sd_x*pop_sd_y);
        return result;
    }
    
    public double verSimilitudPearson(Usuario u1, Usuario u2){
        return ((SimilaridadPear(u1,u2)+1)/2);   
    }
    
}
