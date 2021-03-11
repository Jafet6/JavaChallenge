package br.com.jafethenrique.JavaChallenge.user;

import br.com.jafethenrique.JavaChallenge.utils.exceptions.InvalidPasswordException;
import br.com.jafethenrique.JavaChallenge.utils.hashingMethod.HashingMethod;
import br.com.jafethenrique.JavaChallenge.utils.jwtToken.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.xml.crypto.Data;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private final JWTToken jwtToken;
    private final UserRepository userRepository;
    private final HashingMethod hashingMethod;

    @Autowired
    public UserService(JWTToken jwtToken, UserRepository userRepository, HashingMethod hashingMethod) {
        this.jwtToken = jwtToken;
        this.userRepository = userRepository;
        this.hashingMethod = hashingMethod;
    }

    public String loginUser(UserRequestModel user) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidPasswordException {
        Optional databaseUser = userRepository.findByEmail(user.getEmail());

        if (databaseUser.isEmpty()) throw new EntityNotFoundException("Usuario nao encontrado");

        UserModel userFromDatabase = (UserModel) databaseUser.get();

        if (!hashingMethod.validatePassword(user.getPassword(), userFromDatabase.getPassword()))
            throw new InvalidPasswordException("Senha incorreta");

        String token = jwtToken.createJWT(
                user.getEmail(), "localhost:8080", user.toString(), 100000000);

        return token;
    }

    public String registerNewUser(UserModel user)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        Optional databaseUser = userRepository.findByEmail(user.getEmail());

        if (!databaseUser.isEmpty()) throw new EntityExistsException("Usuario ja cadastrado");

        String token = jwtToken.createJWT(user.getEmail(), "localhost:8080", user.toString(), 100000000);


        String storedPassword = hashingMethod.generateStrongPasswordHash(user.getPassword());
        user.setPassword(storedPassword);

        userRepository.save(user);

        return token;
    }
}
