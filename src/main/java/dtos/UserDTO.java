package dtos;

import entities.Role;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private Integer id;
    private String userName;
    private String password;
    private String email;
    private List<Role> roleList;

    public UserDTO(){

    }

    public UserDTO(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.roleList = user.getRoleList();
    }

    public static List<UserDTO> getDtos(List<User> user) {
        List <UserDTO> userDTOS = new ArrayList<>();
        if (user != null){
            user.forEach(u -> userDTOS.add(new UserDTO(u)));
        }
        return userDTOS;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
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

    public String toString(){
        return "UserDTO{" +
                "id:" + id +
                ", username:" + userName +
                ", password:" + password +
                ", email:" + email + "}";
    }
}
