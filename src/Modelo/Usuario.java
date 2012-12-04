package Modelo;

import Persistencia.DAOUsuarios;
import Persistencia.DAOValoraciones;
import Persistencia.Excepciones.ErrorActualizacionUsuario;
import Persistencia.Excepciones.ErrorCreacionValoracion;
import java.io.Serializable;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity(name="usuarios")
public class Usuario implements Serializable{
    @Id
    @GeneratedValue    
    @Column(name="id")
    private int _id;
    @Column(name="nombre")
    private String _nombre;
    @Column(name="pass")
    private String _pass;
    @Column(name="email")
    private String _email;
    @OneToMany
    @JoinTable(name="valoraciones")
    private List<PeliculaValorada> _valoraciones;

    public Usuario() {}

    public Usuario(String nombre, String pass, String email) {
        _nombre = nombre;
        _pass = pass;
        _email = email;
        _id = 0; //Consultar en BBDD utlimo Id e incrementar 1
        _valoraciones = new ArrayList();
    }

    public boolean login(String pass) {
        if(_pass.equals(Pass.encriptar(pass))){
            return true;
        }else{
            return false;
        }
    }       

    public void nuevaValoracion(Pelicula p, int puntuacion) throws ErrorActualizacionUsuario {
        PeliculaValorada pV = new PeliculaValorada(p, puntuacion);
        this._valoraciones.add(pV);
        DAOUsuarios.instancia().actualizarUsuario(this);
    }

    public void editarValoracion(PeliculaValorada p, int puntuacion) throws ErrorActualizacionUsuario {
        p.editar(puntuacion);
    }

    public AbstractMap getValoraciones() {
        ListIterator it = DAOValoraciones.instancia().buscarPorUsuario(this).listIterator();
        HashMap<String, PeliculaValorada> hMPV = new HashMap<>();
        while(it.hasNext()){
            PeliculaValorada aux = (PeliculaValorada)it.next();
            hMPV.put(aux.getTitulo(), aux);
        }
        return (HashMap<String, PeliculaValorada>)hMPV;            
        //return (HashMap<String,PeliculaValorada>) _valoraciones;
    }

    public PeliculaValorada buscaPeliculaValorada(Pelicula p) {
        ListIterator it = DAOValoraciones.instancia().buscarPorUsuario(this).listIterator();
        while(it.hasNext()){
            PeliculaValorada aux = (PeliculaValorada)it.next();
            if(aux.getTitulo().contains(p.getTitulo())) {
                return aux;
            }
        }
        return null;
    }

    /******************************** Observadores **********************************/

    public int getId(){
        return _id;
    }

    public String getNombre(){
        return _nombre;                    
    }

    public String getEmail(){
        return _email;
    }
}