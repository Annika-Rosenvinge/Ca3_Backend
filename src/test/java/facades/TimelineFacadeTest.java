package facades;

import dtos.TimelineDTO;
import entities.*;
import org.glassfish.hk2.api.Immediate;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class TimelineFacadeTest {

    private static EntityManagerFactory emf;
    private static TimelineFacade timelineFacade;


    public TimelineFacadeTest(){

    }

    @BeforeAll
    static void setUpClass(){
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        timelineFacade = TimelineFacade.getTimelineFacade(emf);
    }

    @AfterAll
    public static void tearDownClass(){

    }

    @BeforeEach
    public void setUp(){
        EntityManager em = emf.createEntityManager();
        List<Role> basic = new ArrayList<>();
        basic.add(new Role("basic"));
        User user = new User("Hans", "pass", "email1", basic);


        Location location = new Location("Q1", "La La Land", "Country");
        Location location1 = new Location("Q2", "Ingenmandsland", "Country");

        Timeline timeline = new Timeline("First", "Det her er den første tidslinje", "1990", "2000", user);
        Spot spot = new Spot("New Years eve", "The night between 1999 and 2000", LocalDate.of(1999, Month.DECEMBER, 31), location, timeline);
        Spot spot1 = new Spot("Christmas", "", LocalDate.of(1999, Month.DECEMBER, 24), location1, timeline);

        try{
            em.getTransaction().begin();
            em.createNamedQuery("Timeline.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            //mangler for spot og location
            em.persist(user);
            em.persist(location);
            em.persist(location1);
            em.persist(timeline);
            em.persist(spot);
            em.persist(spot1);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

    @Disabled
    @Test
    //skal rettes til
    public void createTimelineTest(){
        String name = "New Timeline";
        String description = "The newest timeline";
        String startDate = "1900";
        String endDate = "2022";
        List<Role> basic = new ArrayList<>();
        basic.add(new Role("test"));
        User user = new User("Hans", "pass", "email1", basic);
        Timeline timeline = new Timeline(name, description, startDate, endDate, user);
        TimelineDTO timelineDTO = new TimelineDTO(timeline);

        String expected = timelineDTO.getName();
        String actual = timelineFacade.createTimeline(timelineDTO).getName();

        assertEquals(expected, actual);
    }

    //Virker
    @Test
    public void getAllTimelines(){
        EntityManager em = emf.createEntityManager();
        Role role = new Role("test");
        List<Role> roles = new ArrayList<>();
        User user= new User("Dorte", "Kodeord1", "dorte@email.dk", roles);
        Timeline timeline = new Timeline("My life", "This is a timeline about my life", "1977", "2051", user);
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.persist(timeline);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        List<TimelineDTO> timelinesFound = timelineFacade.getAll(user);
        String actual = timelinesFound.get(0).getName();

        String expected = timeline.getName();

        assertEquals(expected, actual);
    }

    @Disabled
    @Test
    public void testTimelineCount() throws Exception{
        assertEquals(1, timelineFacade.getTimelineCount());
    }
}
