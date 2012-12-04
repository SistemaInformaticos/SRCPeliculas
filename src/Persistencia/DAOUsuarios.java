/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Modelo.Excepciones.*;
import Modelo.Usuario;
import Persistencia.Excepciones.ErrorActualizacionUsuario;
import Persistencia.Excepciones.ErrorBorradoUsuario;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
/**
 *
 * @author Oskar
 */
public class DAOUsuarios {
    static DAOUsuarios instancia = null;

    private DAOUsuarios() {}

    public void crearUsuario(Usuario u) throws ErrorCreacionUsuario{
        EntityManager em = GestorPersistencia.instancia().getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        }catch(EntityExistsException e) {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new ErrorCreacionUsuario();
        }
    }
    
    public Usuario buscarPorId(int id) {
        EntityManager em = GestorPersistencia.instancia().getEntityManager();
        return em.find(Usuario.class, id);
    } 
    
    public List<Usuario> buscarPorNombre(String nombre){
            EntityManager em = GestorPersistencia.instancia().getEntityManager();
            Query q = em.createQuery("select u from usuarios as u  WHERE u.nombre = :nombre");
            q.setParameter("nombre", "%" + nombre + "%");
            try{
                return q.getResultList();
            }catch(NoResultException ne){
                return null;
            }
    }

    public Usuario buscarPorEmail(String email){
            EntityManager em = GestorPersistencia.instancia().getEntityManager();
            Query q = em.createQuery("select u from usuarios u  WHERE email = :email");
            q.setParameter("email", email);
            try{
                return (Usuario)q.getSingleResult();
            }catch(NoResultException ne){
                return null;
            }

    }
    
    public Map<int,Usuario> obtenerUsuarios(){
        Map<int,Usuario> mapa;
        mapa=new HashMap();
        List<Usuario> uList;
            EntityManager em = GestorPersistencia.instancia().getEntityManager();
            Query q = em.createQuery("select u from usuarios u");
            uList = q.getResultList();
            if(uList.isEmpty()){
                return null;
            }else{
                Iterator it=uList.iterator();
                Usuario u;
                while(it.hasNext()){
                    u=(Usuario)it.next();
                    mapa.put(u.getId(), u);
                }
                return mapa;
           }
    }
    
        public void actualizarUsuario(Usuario u) throws ErrorActualizacionUsuario {
        EntityManager em = GestorPersistencia.instancia().getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(u);
            em.getTransaction().commit();
        }
        catch(EntityExistsException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ErrorActualizacionUsuario();
        }
    }

    public void borrar(Usuario u) throws ErrorBorradoUsuario {
        EntityManager em = GestorPersistencia.instancia().getEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
        }
        catch(EntityExistsException e) {
            em.getTransaction().rollback();
            throw new ErrorBorradoUsuario();
        }
    }
    public static DAOUsuarios instancia() {
        if (instancia == null) {
            instancia = new DAOUsuarios();
        }

        return instancia;
    }
}