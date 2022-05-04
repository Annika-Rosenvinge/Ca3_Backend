package dtos;

import entities.Location;
import entities.Spot;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class LocationDTO {
    private Integer id;
    private String name;
    private String type;
    private List<Spot> spots = new ArrayList<>();

    public LocationDTO() {
    }

    public LocationDTO(Integer id, String name, String type, List<Spot> spots) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.spots = spots;
    }

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.name = location.getName();
        this.type = location.getType();
        this.spots = location.getSpotList();
    }

    public static List<LocationDTO> getDtos(List<Location> locations) {
        List <LocationDTO> locationDTOS = new ArrayList<>();
        if (locations != null){
            locations.forEach(l -> locationDTOS.add(new LocationDTO(l)));
        }
        return locationDTOS;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", spots=" + spots +
                '}';
    }
}
