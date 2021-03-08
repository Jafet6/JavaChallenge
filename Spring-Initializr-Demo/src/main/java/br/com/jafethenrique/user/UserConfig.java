//package br.com.jafethenrique.student;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.List;
//
//@Configuration
//public class UserConfig {
//
//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository repository) {
//        return args -> {
//            User jafet = new User(
//                    "Jafet",
//                    "jafet@jafet.com"
//            );
//            User luis = new User(
//                    "Luiz",
//                    "Luis@luis.com"
//            );
//
//            repository.saveAll(
//                    List.of(jafet, luis)
//            );
//        };
//    }
//}
