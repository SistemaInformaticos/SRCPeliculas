package Modelo;
import Modelo.Excepciones.ErrorCreacionUsuario;
import Modelo.Pruebas.Similitud;
import Persistencia.DAOPeliculas;
import Persistencia.DAOUsuarios;
import Persistencia.Excepciones.ErrorConexionBD;
import Persistencia.Excepciones.ErrorCreacionPelicula;
import Persistencia.GestorPersistencia;
import java.util.*;

public class SistemaRecomendacion {
        private Map<int,Usuario> usuarios;
	private Pelicula _peliculas;
	public Usuario _usuario;
	public Similitud _similitud;

	public SistemaRecomendacion() throws ErrorConexionBD {
            GestorPersistencia.crearConexion();
            usuarios=new Hashmap();
            usuarios=getUsuarios();
        }

	public Collection buscarPeliculas(String titulo, int anio) {
            return DAOPeliculas.instancia().buscarPelicula(titulo,anio);		
	}

	public Usuario login(String email, String pass) {
            Usuario u = DAOUsuarios.instancia().buscarPorEmail(email);
            if(u != null){
                return null;
            }else{
                if(u.login(pass)){
                    return u;
                }
                return null;                
            }
	}

	public void nuevoUsuario(Usuario u) throws ErrorCreacionUsuario {
            DAOUsuarios.instancia().crearUsuario(u);
	}

	public void nuevaPelicula(Pelicula p) throws ErrorCreacionPelicula {
            DAOPeliculas.instancia().crearPelicula(p);
	}
   
	public boolean validarDatosUsuario(String email) {
            Usuario u = DAOUsuarios.instancia().buscarPorEmail(email);
            if(u != null){                
                return false;
            }else{
                return true;              
            }	
	}

	public AbstractMap solicitarRecomendacion(Usuario u) {
		throw new UnsupportedOperationException();
	}

	public AbstractMap buscaUsuariosSimiilares(Usuario u) {
		throw new UnsupportedOperationException();
	}
        
        public List<Usuario> getUsuarios(){
            return DAOUsuarios.instancia().obtenerUsuarios();
        }
        
        @Override
        public void finalize() throws Throwable{
            super.finalize();
            GestorPersistencia.desconectar();
        }
}