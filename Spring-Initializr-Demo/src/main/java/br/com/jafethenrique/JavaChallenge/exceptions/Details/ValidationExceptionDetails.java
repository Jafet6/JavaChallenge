package br.com.jafethenrique.JavaChallenge.exceptions.Details;

import br.com.jafethenrique.JavaChallenge.exceptions.Details.ExceptionDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {
    private String fields;
    private final String fieldsMessage;
}
