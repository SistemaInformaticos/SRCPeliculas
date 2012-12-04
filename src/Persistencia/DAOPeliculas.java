/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;
import Modelo.Pelicula;
import Persistencia.Excepciones.ErrorCreacionPelicula;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
/**
 *
 * @author Oskar
 */
public class DAOPeliculas {
    static DAOPeliculas instancia = null;

    private DAOPeliculas() {}

    public void crearPelicula(Pelicula p) throws ErrorCreacionPelicula{
        EntityManager em = GestorPersistencia.instancia().getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        }catch(EntityExistsException e) {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new ErrorCreacionPelicula();
        }
    }
    
    public Pelicula buscarPorId(int id) {
        EntityManager em = GestorPersistencia.instancia().getEntityManager();
        return em.find(Pelicula.class, id);
    }    
    
    public List<Pelicula> buscarPorTitulo(String titulo){
            EntityManager em = GestorPersistencia.instancia().getEntityManager();
            Query q = em.createQuery("select p from Pelicula as p  WHERE p.titulo like :titulo");
            q.setParameter("titulo", "%" + titulo + "%");
            try{
                return q.getResultList();
            }catch(NoResultException ne){
                return null;
            }
    }
    
    
    public List<Pelicula> buscarPorAnio(int anio){
            EntityManager em = GestorPersistencia.instancia().getEntityManager();
            Query q = em.createQuery("select p from Pelicula as p  WHERE p.anio = :anio");
            q.setParameter("anio", anio );
            try{
                return q.getResultList();
            }catch(NoResultException ne){
                return null;
            }
    }
    
    public List<Pelicula> buscarPelicula(String titulo, int anio){
            EntityManager em = GestorPersistencia.instancia().getEntityManager();
            Query q = em.createQuery("select p from Pelicula as p  WHERE p.titulo like :titulo and p.anio = :anio");
            q.setParameter("anio",anio);
            q.setParameter("titulo", "%"+ titulo+"%");
            try{
                return q.getResultList();
            }catch(NoResultException ne){
                return null;
            }
    }
    
    public List<Pelicula> obtenerPeliculas(){
        List<Pelicula> pList;
            EntityManager em = GestorPersistencia.instancia().getEntityManager();
            Query q = em.createQuery("select p from Pelicula p");
            try{
                return q.getResultList();
            }catch(NoResultException ne){
                return null;
            }
    }

    public static DAOPeliculas instancia() {
        if (instancia == null) {
            instancia = new DAOPeliculas();
        }
        return instancia;
    }
}