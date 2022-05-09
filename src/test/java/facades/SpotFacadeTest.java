package facades;

import dtos.SpotDTO;
import dtos.TimelineDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class SpotFacadeTest {

    private static EntityManagerFactory emf;
    private static SpotFacade spotFacade;

    public SpotFacadeTest() {

    }

    @BeforeAll
    static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        spotFacade = SpotFacade.getSpotFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        List<Role> basic = new ArrayList<>();
        basic.add(new Role("basic"));
        User user = new User("Hans", "pass", "email1", basic);

        Location location = new Location("Q1", "La La Land", "Country");
        Location location1 = new Location("Q2", "Ingenmandsland", "Country");

        Timeline timeline = new Timeline("First", "Det her er den første tidslinje",
                "1990", "2000", user);
        Spot spot = new Spot("New Years eve", "The night between 1999 and 2000",
                LocalDate.of(1999, Month.DECEMBER, 31), location, timeline);
        Spot spot1 = new Spot("Christmas", "",
                LocalDate.of(1999, Month.DECEMBER, 24), location1, timeline);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Spot.deleteAllRows").executeUpdate();
            em.createNamedQuery("Location.deleteAllRows").executeUpdate();
            em.createNamedQuery("Timeline.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.persist(user);

            em.persist(location);
            em.persist(location1);

            em.persist(timeline);

            em.persist(spot);
            em.persist(spot1);
            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }

    @Test
    void createSpotTest(){
        String name = "Birthday";
        String description = "My birthday, I turned 13";
        LocalDate timestamp = LocalDate.of(1992, Month.MARCH, 8);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t WHERE t.id = 1", Timeline.class);
        Timeline timeline = query.getSingleResult();

        TypedQuery<Location> query1 = em.createQuery("SELECT l FROM Location l WHERE l.id = 'Q1'", Location.class);
        Location location = query1.getSingleResult();
        em.getTransaction().commit();

        Spot spot= new Spot(name, description, timestamp, location, timeline);
        SpotDTO spotDTO = new SpotDTO(spot);
        TimelineDTO timelineDTO = new TimelineDTO(timeline);

        SpotDTO spotCreated = spotFacade.createSpot(spotDTO, timelineDTO);

        String expected = spotCreated.getName();
        String actual = spotDTO.getName();

        assertEquals(expected, actual);

    }

}
