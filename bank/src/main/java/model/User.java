package model;

import java.util.Date;
import java.util.List;

public class User {

   private Long id;
   private String username;
   private String password;
   private List<Role> roles;
   private Date hire_date;

//    public User(String username, String password, List<Role> roles, Date hire_date) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.roles = roles;
//        this.hire_date = hire_date;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
