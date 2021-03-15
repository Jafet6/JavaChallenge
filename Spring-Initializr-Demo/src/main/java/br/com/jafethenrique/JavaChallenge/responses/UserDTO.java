package br.com.jafethenrique.JavaChallenge.responses;

import br.com.jafethenrique.JavaChallenge.domain.Phones;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private OffsetDateTime created;
    private OffsetDateTime lastLogin;
    private List<Phones> phones;
    private String token;
}
