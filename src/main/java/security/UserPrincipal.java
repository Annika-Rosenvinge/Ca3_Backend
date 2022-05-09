package security;

import entities.User;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserPrincipal implements Principal {

  private String username;
  private List<String> roles = new ArrayList<>();
  private int id;

  /* Create a UserPrincipal, given the Entity class User*/
  public UserPrincipal(User user) {
    this.username = user.getUserName();
    this.roles = user.getRolesAsStrings();
    this.id = user.getId();
  }

  public UserPrincipal(String username, String[] roles) {
    super();
    this.username = username;
    this.roles = Arrays.asList(roles);
  }

  @Override
  public String getName() {
    return username;
  }
  
  public Integer getId(){
    return id;
  }

  public boolean isUserInRole(String role) {
    return this.roles.contains(role);
  }
}