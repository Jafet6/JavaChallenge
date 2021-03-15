package br.com.jafethenrique.JavaChallenge.service;

import br.com.jafethenrique.JavaChallenge.responses.UserDTO;
import br.com.jafethenrique.JavaChallenge.domain.User;
import br.com.jafethenrique.JavaChallenge.mappers.UserMapper;
import br.com.jafethenrique.JavaChallenge.repository.UserRepository;
import br.com.jafethenrique.JavaChallenge.utils.DateUTC;
import br.com.jafethenrique.JavaChallenge.utils.exceptions.EmptyEmailException;
import br.com.jafethenrique.JavaChallenge.utils.exceptions.InvalidPasswordException;
import br.com.jafethenrique.JavaChallenge.utils.hashingMethod.HashingMethod;
import br.com.jafethenrique.JavaChallenge.utils.jwtToken.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.time.*;
import java.util.Optional;

@Service
public class UserService {
    private final JWTToken jwtToken;
    private final UserRepository userRepository;
    private final HashingMethod hashingMethod;
    private final UserMapper userMapper;
    private final DateUTC utcCurrentDate;

    @Autowired
    public UserService(JWTToken jwtToken, UserRepository userRepository, HashingMethod hashingMethod, UserMapper userMapper, DateUTC utcCurrentDate) {
        this.jwtToken = jwtToken;
        this.userRepository = userRepository;
        this.hashingMethod = hashingMethod;
        this.userMapper = userMapper;
        this.utcCurrentDate = utcCurrentDate;
    }

    public User findUserByEmailOrThrowAnException(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));
    }

    public UserDTO loginUser(User user)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidPasswordException, IOException, ParseException {
        Optional databaseUser = userRepository.findByEmail(user.getEmail());

        if (databaseUser.isEmpty()) throw new EntityNotFoundException("Usuario nao encontrado");
//        User databaseUser = findUserByEmailOrThrowAnException(user.getEmail());
        User userFromDatabase = (User) databaseUser.get();

        OffsetDateTime UTCCurrentDate = utcCurrentDate.getCurrentUtcTime();
        userFromDatabase.setLastLogin(UTCCurrentDate);

        User userPersisted = userRepository.save(userFromDatabase);

        if (!hashingMethod.validatePassword(user.getPassword(), userFromDatabase.getPassword()))
            throw new InvalidPasswordException("Senha incorreta");

        String token = jwtToken.createJWT(
                user.getEmail(), "localhost:8080", user.toString(), 100000000);

        UserDTO response = userMapper.convertUserModelToDto(userPersisted);
        response.setToken(token);

        return response;
    }

    public UserDTO registerNewUser(User user)
            throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, EmptyEmailException, ParseException {

        String userEmail = user.getEmail();

        if (userEmail.isEmpty()) throw new EmptyEmailException("Email invalido");

        String userStandardEmail = userEmail.trim().toLowerCase();
        user.setEmail(userStandardEmail);

        Optional databaseUser = userRepository.findByEmail(user.getEmail());

        if (!databaseUser.isEmpty()) throw new EntityExistsException("Usuario ja cadastrado");

        String token = jwtToken.createJWT(user.getEmail(), "localhost:8080", user.toString(), 100000000);

        String storedPassword = hashingMethod.generateStrongPasswordHash(user.getPassword());
        user.setPassword(storedPassword);


        OffsetDateTime UTCCurrentDate = utcCurrentDate.getCurrentUtcTime();
        user.setCreated(UTCCurrentDate);
        user.setLastLogin(UTCCurrentDate);

        User userPersisted = userRepository.save(user);

        UserDTO response = userMapper.convertUserModelToDto(userPersisted);

        response.setToken(token);

        return response;
    }
}
