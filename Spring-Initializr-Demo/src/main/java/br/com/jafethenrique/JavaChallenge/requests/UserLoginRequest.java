package br.com.jafethenrique.JavaChallenge.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
