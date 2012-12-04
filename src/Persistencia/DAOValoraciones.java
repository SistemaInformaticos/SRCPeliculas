/*
 * To change this teMplate, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;
import Modelo.Pelicula;
import Modelo.PeliculaValorada;
import Modelo.Usuario;
import Persistencia.Excepciones.ErrorActualizacionUsuario;
import Persistencia.Excepciones.ErrorCreacionValoracion;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
/**
 *
 * @author Oskar
 */
public class DAOValoraciones {
    static DAOValoraciones instancia = null;

    private DAOValoraciones() {}

    public void crearValoracion(PeliculaValorada p) throws ErrorCreacionValoracion{
        EntityManager em = GestorPersistencia.instancia().getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        }catch(EntityExistsException e) {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new ErrorCreacionValoracion();
        }
    }
    
    public List<PeliculaValorada> buscarPorUsuario(Usuario u){
            EntityManager em = GestorPersistencia.instancia().getEntityManager();
            Query q = em.createQuery("select u from peliculas as u  WHERE u.ide like :usuario.id");
            q.setParameter("id", "%" + u.getId() + "%");
            try{
                return q.getResultList();
            }catch(NoResultException ne){
                return null;
            }
    }
    
    public void actualizarValoracion(PeliculaValorada p) throws ErrorActualizacionUsuario {
        EntityManager em = GestorPersistencia.instancia().getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
        }catch(EntityExistsException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ErrorActualizacionUsuario();
        }
    }
    
    public float consultarValoracionMedia(){
        EntityManager em = GestorPersistencia.instancia().getEntityManager();
        int total = 0;
        Query q = em.createQuery("select valoracion from peliculasValoradas");            
        for(int i=0; i<q.getResultList().size(); i++){
            total = total + (int)q.getResultList().get(i);               
        }
        return total/q.getResultList().size();
    }
        
    public static DAOValoraciones instancia() {
        if (instancia == null) {
            instancia = new DAOValoraciones();
        }

        return instancia;
    }
}