package Persistencia;

import Persistencia.Excepciones.ErrorConexionBD;
import java.io.*;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GestorPersistencia {
    EntityManagerFactory emf;
    EntityManager em;
    
    static GestorPersistencia instancia = null;
    
    private GestorPersistencia() {
        emf = Persistence.createEntityManagerFactory("SRCPeliculasPU");
        em = emf.createEntityManager();
    }
    
    public EntityManager getEntityManager() {
        return em;
    }
        
    public static void crearConexion() throws ErrorConexionBD {
        if (instancia == null) {
            instancia = new GestorPersistencia();
        }
    }
    
    public static GestorPersistencia instancia() {
        return instancia;
    }
    
    public static void desconectar() {
        if (instancia != null) {
            instancia.em.getTransaction().begin();
            instancia.em.createNativeQuery("shutdown").executeUpdate();
            instancia.em.getTransaction().commit();
            
            instancia.em.close();
            instancia.emf.close();
            instancia = null;
        }
    }
    /**
     * Carga de disco los datos del siistema
     * @throws FileNotFoundException Fichero no encontrado
     * @throws IOException Entrada/Salida erronea
     * @throws ClassNotFoundException Clases distintas, erroneas o no encontrada
     */
    public static Object cargar (Object o)throws IOException, ClassNotFoundException{
        Properties prop=new Properties();
        FileInputStream is=new FileInputStream("build/classes/Modelo/Preferencias");
        prop.load(is);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(prop.getProperty("datos")));
        Object s=ois.readObject();
        return s;
    }
    
    /**
     * Guarda los datos en disco del sistema
     * @throws FileNotFoundException Fichero no encontrado
     * @throws IOException Entrada/Salida erronea
     */
    public static void guardar (Object o)throws FileNotFoundException, IOException{
        Properties prop=new Properties();
        FileInputStream is=new FileInputStream("build/classes/Modelo/Preferencias");
        prop.load(is);
        try (FileOutputStream ostream = new FileOutputStream(prop.getProperty("datos"))) {
            ObjectOutputStream oop = new ObjectOutputStream(ostream);
            oop.writeObject(o);
            oop.flush();
        }
    }
    
   
}