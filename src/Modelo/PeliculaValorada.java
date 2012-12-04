package Modelo;

import Persistencia.DAOValoraciones;
import Persistencia.Excepciones.ErrorActualizacionUsuario;
import java.io.Serializable;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;

@Entity(name="valoraciones")
public class PeliculaValorada extends Pelicula implements Serializable{
	@Column(name="puntuacion")
        private int _puntuacion;
        @Temporal(javax.persistence.TemporalType.DATE)
        @Column(name="fecha")
	private Date _fecha;
        
        public PeliculaValorada(){}

	public PeliculaValorada(Pelicula p, int punteacion) {
            super(p.getTitulo(), p.getAnio(), p.getId());		
	}

	public void editar(int puntuacion) throws ErrorActualizacionUsuario{
            _puntuacion=puntuacion;            
            DAOValoraciones.instancia().actualizarValoracion(this);          
	}
        
       /************************************** Observadores ************************************/

	public int getPuntuacion() {
            return _puntuacion;
	}
        
        public Date getFecha(){
            return _fecha;
        }
}