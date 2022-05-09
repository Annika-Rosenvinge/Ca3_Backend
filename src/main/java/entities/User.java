package entities;

import dtos.UserDTO;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "User.deleteAllRows", query = "DELETE FROM User")
@Table(name = "User")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Timeline> timelinelist;

    public User(){

    }

    public User(Integer id, String userName, String password, String email,  List<Role> roleList){
        this.id = id;
        this.userName = userName;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.email = email;
    }

    public User(String userName, String password, String email, List<Role> roleList){
        this.userName = userName;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.email = email;
    }
    public User(String userName, String password, String email, List<Role> roleList, List<Timeline> timelinelist){
        this.userName = userName;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.email = email;
        this.timelinelist = timelinelist;
    }

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.userName = userDTO.getUserName();
        this.password = userDTO.getPassword();
        this.email = userDTO.getEmail();
    }

    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, password);
    }

    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRole());
        });
        return rolesAsStrings;
    }

    public List<Timeline> getTimelinelist() {
        return timelinelist;
    }

    public void setTimelinelist(List<Timeline> timelinelist) {
        this.timelinelist = timelinelist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roleList=" + roleList +
                ", timelinelist=" + timelinelist +
                '}';
    }
}
