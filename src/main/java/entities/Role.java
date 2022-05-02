package entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "role_name", updatable = true, nullable = false)
    private String role;

    @ManyToMany (mappedBy = "roleList")
    private List<User> userList;

    public Role(){

    }

    public Role(Integer id, String role, List<User> userList) {
        this.id = id;
        this.role = role;
        this.userList = userList;
    }

    public Role (String role, List<User> userList){
        this.role = role;
        this.userList = userList;
    }

    public Role(String role){
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
