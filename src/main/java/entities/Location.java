package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Location implements Serializable {
    @Id
    @Column(name = "wikidata_id", nullable = false, length = 100)
    private Integer id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @OneToMany(mappedBy = "location", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Spot> spotList = new ArrayList<>();

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


    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
