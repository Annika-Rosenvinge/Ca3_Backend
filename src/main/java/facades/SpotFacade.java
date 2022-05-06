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

    //skal skrives om
    //for at kunne oprette et spot, så skal der være en timeline med som parameter
    //for ellers så kan spottet ikke oprettes (den skal bruge id'et men hele entitien skal skrives ind)
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
    /*PSEUDO KODE
    * Create spot (Spot spot, Timeline timeline)
    *Spot spot = new spot...
    * Timeline timline = new Timeline
    * Find timeline, med entitymanager.
    * entity manager
    * try
    * begin
    * persist
    * commit
    * finally
    * close
    * return spot
    * */

}
