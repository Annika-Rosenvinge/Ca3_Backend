package facades;

import dtos.TimelineDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Disabled
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

    @Test
    public void createTimelineTest(){
        String name = "New Timeline";
        String description = "The newest timeline";
        String startDate = "1900";
        String endDate = "2022";
        List<Role> basic = new ArrayList<>();
        basic.add(new Role("basic"));
        User user = new User("Hans", "pass", "email1", basic);
        Timeline timeline = new Timeline(name, description, startDate, endDate, user);
        TimelineDTO timelineDTO = new TimelineDTO(timeline);

        String expected = timelineDTO.getName();
        String actual = timelineFacade.createTimeline(timelineDTO).getName();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllTimelines(){
        int expected = 1;
        int actual = timelineFacade.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    public void testTimelineCount() throws Exception{
        assertEquals(1, timelineFacade.getTimelineCount());
    }
}
