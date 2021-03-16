package br.com.jafethenrique.JavaChallenge.requests;

import br.com.jafethenrique.JavaChallenge.domain.Phones;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserRegisterRequest {
    @NotEmpty(message = "Email nao pode ser vazia")
    private String email;
    @NotEmpty(message = "Senha nao pode ser vazia")
    private String password;
    @NotEmpty(message = "Nome nao pode ser vazia")
    private String name;
    private List<Phones> phones;
}
