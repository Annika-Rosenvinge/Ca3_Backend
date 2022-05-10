/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManagerFactory;

import dtos.LocationDTO;
import dtos.SpotDTO;
import dtos.TimelineDTO;
import dtos.UserDTO;
import entities.*;
import utils.EMF_Creator;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        UserFacade userFacade = UserFacade.getUserFacade(emf);
        LocationFacade locationFacade = LocationFacade.getLocationFacade(emf);
        TimelineFacade timelineFacade = TimelineFacade.getTimelineFacade(emf);
        SpotFacade spotFacade = SpotFacade.getSpotFacade(emf);

        List<Role> admin = new ArrayList<>();
        List<Role> basic = new ArrayList<>();
        admin.add(new Role("admin"));
        basic.add(new Role("basic"));

        UserDTO user1 = userFacade.create(new UserDTO(new User("Hanne", "Abekat1", "hanne@email.dk", admin)));
        userFacade.create(new UserDTO(new User("Klaus", "Sommerfugl24", "Klaus@email.dk", admin)));
        userFacade.create(new UserDTO(new User("Søren", "password1234", "s.ren@mail.com", basic)));
        //LOCATION
        Location location = new Location("Q889", "Afghanistan", "Country");
         locationFacade.createLocation(location.getId(), location.getName(),location.getType());
        //TIMELINE
        Timeline timeline = new Timeline("timeline", "first timeline", "1999", "2000", new User(user1));
        timelineFacade.createTimeline(new TimelineDTO(timeline));
        //SPOTS
        //spotFacade.createSpot(new SpotDTO(new Spot("january", "first month",LocalDate.of(1999, Month.JANUARY, 1),location, timeline)));

    }

    public static void main(String[] args) {
        populate();
    }
}
