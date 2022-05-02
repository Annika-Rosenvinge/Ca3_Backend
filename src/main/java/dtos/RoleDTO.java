package dtos;

import entities.Role;
import entities.User;

import java.util.List;

public class RoleDTO {
    Integer id;
    String role;
    List<User> userList;

    public RoleDTO (Role role){
        this.id = role.getId();
        this.role = role.getRole();
        this.userList = role.getUserList();
    }

    public RoleDTO (){
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

    @Override
    public String toString(){
        return "RoleDTO{" +
                "id:" + id +
                ", role:" + role +
                ", userList" + userList + "}";
    }
}
