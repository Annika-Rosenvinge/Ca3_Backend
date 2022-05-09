package facades;

import dtos.LocationDTO;
import entities.Location;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class LocationFacade {
    private static LocationFacade instance;
    private static EntityManagerFactory emf;

    private LocationFacade(){

    }

    public static LocationFacade getLocationFacade(EntityManagerFactory _emf){
        if(instance == null){
            emf=_emf;
            instance = new LocationFacade();
        }
        return instance;
    }
    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    public LocationDTO createLocation(LocationDTO locationDTO){
        Location location = new Location(locationDTO.getId(), locationDTO.getName(), locationDTO.getType());
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        return new LocationDTO(location);
    }
}