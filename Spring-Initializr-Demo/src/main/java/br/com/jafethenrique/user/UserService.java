package br.com.jafethenrique.user;

import br.com.jafethenrique.utils.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
//
    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public String findUserByEmail(User user) {
        Optional databaseUser = userRepository.findByEmail(user.getEmail());

        if (databaseUser.isEmpty()) throw new EntityNotFoundException("Usuario nao encontrado");

        String token = JWTToken.createJWT(user.getEmail(), "localhost:8080", user.toString(), 100000000);
        return token;
    }

    public String addNewUser(User user) {
        System.out.println(user);
        Optional databaseUser = userRepository.findByEmail(user.getEmail());

        if (!databaseUser.isEmpty()) throw new EntityExistsException("Usuario ja cadastrado");

        String token = JWTToken.createJWT(user.getEmail(), "localhost:8080", user.toString(), 100000000);
        userRepository.save(user);
        return token;
    }
}
