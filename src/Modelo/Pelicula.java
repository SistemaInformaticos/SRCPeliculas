package Modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="peliculas")
public class Pelicula implements Serializable{
    @Id
    @GeneratedValue       
    @Column(name="id")
    private int _id;
    @Column(name="titulo")
    private String _titulo;
    @Column(name="anio")
    private int _anio;

    public Pelicula(){}

    public Pelicula(String titulo, int anio, int id) {
        _titulo = titulo;
        _anio = anio;
        _id = id;
    }
    
   /******************************** Observadores **********************************/

    public String getTitulo() {
        return this._titulo;
    }

    public int getAnio() {
        return this._anio;
    }

    public int getId() {
        return this._id;
    }
}