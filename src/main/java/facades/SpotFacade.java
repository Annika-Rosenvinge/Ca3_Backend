package facades;

import dtos.SpotDTO;
import dtos.TimelineDTO;
import entities.Spot;
import entities.Timeline;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

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

    //test er lavet og virker
    public SpotDTO createSpot(SpotDTO spotDTO, TimelineDTO timelineDTO){
        EntityManager em = getEntityManager();
        int timelineID = timelineDTO.getId();
        Timeline timeline = new Timeline(timelineDTO);
        timeline.setId(timelineID);
        spotDTO.setTimeline(timeline);
        //refactor spot entity and spotdto!
        Spot spot = new Spot(spotDTO.getName(), spotDTO.getDescription(), spotDTO.getTimestamp(), spotDTO.getLocation(), spotDTO.getTimeline());
        try{
            em.getTransaction().begin();;
            em.persist(spot);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        return new SpotDTO(spot);
    }
    /*PSEUDO KODE til createSpot - gemmes
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

    public SpotDTO editSpot(SpotDTO spotDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Spot spot = em.find(Spot.class, spotDTO.getId());
            spot.setName(spotDTO.getName());
            spot.setDescription(spotDTO.getDescription());
            spot.setLocation(spotDTO.getLocation());
            spot.setTimeStamp(spotDTO.getTimestamp());
            em.getTransaction().commit();
            return new SpotDTO(spot);
        } finally {
            em.close();
        }
    }

    public SpotDTO getSpotById(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Spot spot = em.find(Spot.class, id);
            em.getTransaction().commit();
            return new SpotDTO(spot);
        } finally {
            em.close();
        }
    }


    public List<SpotDTO> timeSortedSpots(TimelineDTO timelineDTO){
        //Take in timeline ass parameter
        //get timeline id
        //search for spots where timeline id = the given id
        //sort the spots after date the oldest first - find the correct way for LocalDate
        //return the list

     return null;
    }

}
