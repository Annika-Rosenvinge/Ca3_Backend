package facades;

import dtos.SpotDTO;
import entities.Spot;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SpotFacade {
    private static SpotFacade instance;
    private static EntityManagerFactory emf;

    private SpotFacade(){

    }
    public static SpotFacade getSpotFacade(EntityManagerFactory _emf){
        if (instance == null){
            emf = _emf;
            instance = new SpotFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public SpotDTO createSpot(SpotDTO spotDTO){
        Spot spot = new Spot(spotDTO.getName(), spotDTO.getDescription(),spotDTO.getTimestamp(), spotDTO.getLocation(), spotDTO.getTimeline());
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(spot);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
        return new SpotDTO(spot);
    }

}
