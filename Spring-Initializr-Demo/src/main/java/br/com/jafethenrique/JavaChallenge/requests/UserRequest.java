package br.com.jafethenrique.JavaChallenge.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Component
public class UserRequest {
    @NotEmpty(message = "Email nao pode ser vazia")
    private String email;
    @NotEmpty
    private String password;
}
