package br.com.jafethenrique.JavaChallenge;

import br.com.jafethenrique.JavaChallenge.mappers.UserMapper;
import br.com.jafethenrique.JavaChallenge.utils.DateUTC;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public UserMapper userMapper() {
        return new UserMapper(new ModelMapper());
    }

    @Bean
    public DateUTC dateUTC() {
        return new DateUTC();
    }
}


