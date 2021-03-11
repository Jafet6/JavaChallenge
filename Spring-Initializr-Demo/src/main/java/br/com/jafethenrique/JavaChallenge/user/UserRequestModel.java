package br.com.jafethenrique.JavaChallenge.user;

public class UserRequestModel {

    private String email;
    private String password;

    public UserRequestModel(String email, String senha) {
        this.email = email;
        this.password = senha;
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
}
