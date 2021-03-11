package br.com.jafethenrique.JavaChallenge.user;

import java.util.Date;

public class UserResponseModel {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Date created;
//    private String modified;
    private Date last_login;
    private String token;

    public UserResponseModel(Long id, String name, String email, String password, Date created, Date modified, Date last_login, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.created = created;
//        this.modified = modified;
        this.last_login = last_login;
        this.token = token;
    }

    public UserResponseModel() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

//    public String getModified() {
//        return modified;
//    }
//
//    public void setModified(String modified) {
//        this.modified = modified;
//    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
