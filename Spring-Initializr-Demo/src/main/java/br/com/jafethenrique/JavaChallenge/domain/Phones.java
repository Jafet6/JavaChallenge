package br.com.jafethenrique.JavaChallenge.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "Phones")
@Data
public class Phones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String ddd;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userModel;

    @Autowired
    public Phones(String number, String ddd, User userModel) {
        this.number = number;
        this.ddd = ddd;
        this.userModel = userModel;
    }

}
