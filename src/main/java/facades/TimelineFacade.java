package facades;

import dtos.TimelineDTO;
import dtos.TimelinesDTO;
import entities.Timeline;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.sql.Time;
import java.util.List;

public class TimelineFacade {

    private static TimelineFacade instance;
    private static EntityManagerFactory emf;


    private TimelineFacade() {

    }

    public static TimelineFacade getTimelineFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TimelineFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //Test er lavet, men skal rettes ind
    public TimelineDTO createTimeline(TimelineDTO timelineDTO) throws IllegalStateException {
        Timeline timeline = new Timeline(timelineDTO.getName(), timelineDTO.getDescription(), timelineDTO.getStartDate(),
                timelineDTO.getEndDate(), timelineDTO.getUser());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(timeline);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new TimelineDTO(timeline);
    }

    public TimelineDTO deleteTimeline(int id) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        Timeline timeline = em.find(Timeline.class, id);
        if (timeline == null) {
            throw new WebApplicationException("no timelines matches the id");
        } else {
            try {
                em.getTransaction().begin();
                em.createNativeQuery("DELETE FROM Timeline WHERE id = ?").setParameter(1, timeline.getId()).executeUpdate();
                em.remove(timeline);
                em.getTransaction().commit();

                return new TimelineDTO(timeline);
            } finally {
                em.close();
            }
        }
    }
        //Sprint 2
    /*public TimelineDTO deleteTimeline(Integer id){

    }*/

        //denne metode gemmes til rapporten
       /*public List<TimelineDTO> getAll () {
            EntityManager em = emf.createEntityManager();
            //TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t", Timeline.class);
            TypedQuery<Timeline> query1 = em.createQuery("SELECT t FROM Timeline t WHERE t.user.id = :id", Timeline.class);
            List<Timeline> timelines = query1.getResultList();
            return TimelineDTO.getDtos(timelines);
        }*/

        //Test er lavet og virker
        public List<TimelineDTO> getAll (User user){
            EntityManager em = emf.createEntityManager();
            try {
                User Uid = em.find(User.class, user.getId());
                int id = Uid.getId();
                TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t WHERE t.user.id = :id", Timeline.class);
                query.setParameter("id", id);
                List<Timeline> timelines = query.getResultList();
                return TimelineDTO.getDtos(timelines);
            } finally {
                em.close();
            }
        }
    public TimelinesDTO getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Timeline> query = em.createNamedQuery("Timeline.getAllRows", Timeline.class);
            List<Timeline> result = query.getResultList();
            TimelinesDTO dto = new TimelinesDTO(result);
            em.getTransaction().commit();
            return dto;
        } finally {
            em.close();
        }
    }
/*
        //test mangler
        public Long getTimelineCount () {
            EntityManager em = emf.createEntityManager();
            try {
                Long timelineCount = (Long) em.createQuery("SELECT COUNT(t) FROM Timeline t").getSingleResult();
                return timelineCount;
            } finally {
                em.close();
            }
        }*/

    }
