package br.com.jafethenrique.JavaChallenge.user;

import br.com.jafethenrique.JavaChallenge.utils.exceptions.InvalidPasswordException;
import br.com.jafethenrique.JavaChallenge.utils.hashingMethod.HashingMethod;
import br.com.jafethenrique.JavaChallenge.utils.jwtToken.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public String loginUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidPasswordException {
        Optional databaseUser = userRepository.findByEmail(user.getEmail());

        if (databaseUser.isEmpty()) throw new EntityNotFoundException("Usuario nao encontrado");

        User userFromDatabase = (User) databaseUser.get();

        if (!HashingMethod.validatePassword(user.getPassword(), userFromDatabase.getPassword())) throw new InvalidPasswordException("Senha incorreta");

        String token = JWTToken.createJWT(user.getEmail(), "localhost:8080", user.toString(), 100000000);
        return token;
    }

    public String registerNewUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Optional databaseUser = userRepository.findByEmail(user.getEmail());

        if (!databaseUser.isEmpty()) throw new EntityExistsException("Usuario ja cadastrado");

        String token = JWTToken.createJWT(user.getEmail(), "localhost:8080", user.toString(), 100000000);

        String storedPassword = HashingMethod.generateStrongPasswordHash(user.getPassword());
        user.setPassword(storedPassword);
        userRepository.save(user);

        return token;
    }
}
