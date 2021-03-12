package br.com.jafethenrique.JavaChallenge.user;

import br.com.jafethenrique.JavaChallenge.DTO.UserDTO;
import br.com.jafethenrique.JavaChallenge.mappers.UserMapper;
import br.com.jafethenrique.JavaChallenge.utils.exceptions.EmptyEmailException;
import br.com.jafethenrique.JavaChallenge.utils.exceptions.InvalidPasswordException;
import br.com.jafethenrique.JavaChallenge.utils.hashingMethod.HashingMethod;
import br.com.jafethenrique.JavaChallenge.utils.jwtToken.JWTToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {
    private final JWTToken jwtToken;
    private final UserRepository userRepository;
    private final HashingMethod hashingMethod;
    private final UserMapper userMapper;

    @Autowired
    public UserService(JWTToken jwtToken, UserRepository userRepository, HashingMethod hashingMethod, UserMapper userMapper) {
        this.jwtToken = jwtToken;
        this.userRepository = userRepository;
        this.hashingMethod = hashingMethod;
        this.userMapper = userMapper;
    }

    public UserDTO loginUser(UserModel user)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidPasswordException, IOException {
        Optional databaseUser = userRepository.findByEmail(user.getEmail());

        if (databaseUser.isEmpty()) throw new EntityNotFoundException("Usuario nao encontrado");

        UserModel userFromDatabase = (UserModel) databaseUser.get();

        if (!hashingMethod.validatePassword(user.getPassword(), userFromDatabase.getPassword()))
            throw new InvalidPasswordException("Senha incorreta");

        String token = jwtToken.createJWT(
                user.getEmail(), "localhost:8080", user.toString(), 100000000);

        UserDTO response = userMapper.convertUserModelToDto(userFromDatabase);
        response.setToken(token);

        return response;
    }

    public UserDTO registerNewUser(UserModel user)
            throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, EmptyEmailException {

        String userEmail = user.getEmail();

        if (userEmail.isEmpty()) throw new EmptyEmailException("Email invalido");

        String userStandardEmail = userEmail.trim().toLowerCase();
        user.setEmail(userStandardEmail);

        Optional databaseUser = userRepository.findByEmail(user.getEmail());

        if (!databaseUser.isEmpty()) throw new EntityExistsException("Usuario ja cadastrado");

        String token = jwtToken.createJWT(user.getEmail(), "localhost:8080", user.toString(), 100000000);

        String storedPassword = hashingMethod.generateStrongPasswordHash(user.getPassword());
        user.setPassword(storedPassword);

        userRepository.save(user);

        UserDTO response = userMapper.convertUserModelToDto(user);

        response.setToken(token);

        return response;
    }
}
