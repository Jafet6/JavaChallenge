package br.com.jafethenrique.JavaChallenge.utils.phoneObject;

import br.com.jafethenrique.JavaChallenge.user.UserModel;

import javax.persistence.*;

@Entity
@Table(name = "Phones")
public class Phones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String ddd;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserModel userModel;

    public Phones(String number, String ddd) {
        this.number = number;
        this.ddd = ddd;
    }

    public Phones() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

}
