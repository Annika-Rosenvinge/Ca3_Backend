package facades;

import dtos.TimelineDTO;
import entities.Timeline;
import entities.User;
import errorhandling.NotFoundException;

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
    //den skal specificeres efter bruger
    /*getAllTimelines(user)
     *user.getid
     * find bruger ud fra id'et
     * find timelines ud fra bruger id
     * typed query
     * list = query.get result
     * return
     * */
    /*public List<TimelineDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        //TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t", Timeline.class);
        int id = 1;
        TypedQuery<Timeline> query1 = em.createQuery("SELECT t FROM Timeline t WHERE t.user.id = :id", Timeline.class);
        List<Timeline> timelines = query1.getResultList();
        return TimelineDTO.getDtos(timelines);
    }*/

    //Test er lavet og virker
    public List<TimelineDTO> getAll(User user){
        EntityManager em = emf.createEntityManager();
        try {
            User Uid = em.find(User.class, user.getId());
            int id = Uid.getId();
            TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t WHERE t.user.id = :id", Timeline.class);
            query.setParameter("id", id);
            List<Timeline> timelines = query.getResultList();
            return TimelineDTO.getDtos(timelines);
        }finally {
            em.close();
        }
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




    //delete timeline ()
    public Timeline deleteTimeline(int id) throws NotFoundException {
        EntityManager em = getEntityManager();
        Timeline tl = em.find(Timeline.class, id);
        if (tl == null)
            throw new NotFoundException("Could not remove Timeline with id: "+id);
        em.getTransaction().begin();
        em.remove(tl);
        em.getTransaction().commit();
        return tl;
    }
}