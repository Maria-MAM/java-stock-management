package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerService {

    private static EntityManagerService instance;
    private final PropertiesService ps;
    private final EntityManager em;

    private EntityManagerService() {
        // create entities & structure
        // run initial SQL script
        // insert initial data
        ps = PropertiesService.getInstance();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BBStorePU", ps.getProp());
        em = emf.createEntityManager();
    }

    public static EntityManagerService getInstance() {
        if (instance == null) {
            instance = new EntityManagerService();
        }
        return instance;
    }

    public EntityManager getEm() {
        return em;
    }
}
