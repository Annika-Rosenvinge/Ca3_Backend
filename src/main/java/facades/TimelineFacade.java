package facades;

import dtos.TimelineDTO;
import entities.Timeline;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class TimelineFacade {

    private static TimelineFacade instance;
    private static EntityManagerFactory emf;


    private TimelineFacade(){

    }

    public static TimelineFacade getTimelineFacade(EntityManagerFactory _emf){
        if (instance == null){
            emf=_emf;
            instance = new TimelineFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    //Test er lavet, men skal rettes ind
    public TimelineDTO createTimeline(TimelineDTO timelineDTO){
        Timeline timeline = new Timeline(timelineDTO.getName(), timelineDTO.getDescription(), timelineDTO.getStartDate(),
                timelineDTO.getEndDate(), timelineDTO.getUser());
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(timeline);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
        return new TimelineDTO(timeline);
    }

    //Det er vigtigt at denne her kan sortere i timestamps, så man ikke skal skrives spots ind i rækkefølge
    /*public TimelineDTO sortSpotsByTimeStamp(){

    }


    //Sprint 2
    public TimelineDTO deleteTimeline(Long id){

    }*/

    //test mangler
    public List<TimelineDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t", Timeline.class);
        List<Timeline> timelines = query.getResultList();
        return TimelineDTO.getDtos(timelines);
    }

    //test mangler
    public Long getTimelineCount(){
        EntityManager em = emf.createEntityManager();
        try {
            Long timelineCount = (Long) em.createQuery("SELECT COUNT(t) FROM Timeline t").getSingleResult();
            return timelineCount;
        } finally {
            em.close();
        }

    }
}
