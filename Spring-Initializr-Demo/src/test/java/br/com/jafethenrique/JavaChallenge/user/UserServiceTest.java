package br.com.jafethenrique.JavaChallenge.user;

import br.com.jafethenrique.JavaChallenge.responses.UserDataResponse;
import br.com.jafethenrique.JavaChallenge.domain.User;
import br.com.jafethenrique.JavaChallenge.mappers.UserMapper;
import br.com.jafethenrique.JavaChallenge.domain.Phones;
import br.com.jafethenrique.JavaChallenge.repository.UserRepository;
import br.com.jafethenrique.JavaChallenge.service.UserService;
import br.com.jafethenrique.JavaChallenge.exceptions.EmptyEmailException;
import br.com.jafethenrique.JavaChallenge.exceptions.InvalidPasswordException;
import br.com.jafethenrique.JavaChallenge.utils.hashingMethod.HashingMethod;
import br.com.jafethenrique.JavaChallenge.utils.jwtToken.JWTToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private String token;
    private User user;
    private UserDataResponse mockedUserDataResponse;
    private String hashedPassword;
    private List<Phones> mockedPhones;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTToken jwtToken;

    @Mock
    private HashingMethod hashingMethod;

    @InjectMocks
    private UserService userServiceMock;


    @BeforeEach
    public void beforeEach() {
        Phones phonesMocked = new Phones("123123123", "31");

        ArrayList listOfPhones = new ArrayList();
        listOfPhones.add(phonesMocked);

        mockedPhones = listOfPhones;

        User mockedUser = new User("jafet", "jafet@jafet.com", "123123123", listOfPhones);

        user = mockedUser;

        token = "token";

        hashedPassword = "password";

        UserDataResponse userDataResponse = new UserDataResponse(1L, "jafet", "jafet@jafet.com", "123123123", OffsetDateTime.now(ZoneOffset.UTC), OffsetDateTime.now(ZoneOffset.UTC), listOfPhones, token);

        mockedUserDataResponse = userDataResponse;
    }

    @Test
    @DisplayName("Should register a new User")
    void successfulRegisterNewUserTest() throws NoSuchAlgorithmException, ParseException, InvalidKeySpecException, EmptyEmailException, IOException {

        Mockito.when(userRepository.findByEmail("jafet@jafet.com"))
                .thenReturn(Optional.<User>empty());

        Mockito.when(jwtToken.createJWT(user.getEmail(), "localhost:8080", user.toString(), 100000000))
                .thenReturn(token);

        Mockito.when(hashingMethod.generateStrongPasswordHash(user.getPassword())).thenReturn(hashedPassword);

        Mockito.when(userRepository.save(user)).thenReturn(user);

        Mockito.when(userMapper.convertUserModelToDto(user)).thenReturn(mockedUserDataResponse);

        assertEquals(mockedUserDataResponse, userServiceMock.registerNewUser(user));
    }

    @Test
    @DisplayName("Should display Usuaria ja cadastrado")
    void userAlredyExists() throws NoSuchAlgorithmException, ParseException, InvalidKeySpecException, EmptyEmailException, IOException {

        Mockito.when(userRepository.findByEmail("jafet@jafet.com"))
                .thenReturn(Optional.of(user));

        try {
            userServiceMock.registerNewUser(user);
            fail();
        } catch (EntityExistsException exception) {
            assertEquals("Usuario ja cadastrado", exception.getMessage());
        }

    }

    @Test
    @DisplayName("Should display Usuaria ja cadastrado")
    void invalidEmail() throws NoSuchAlgorithmException, ParseException, InvalidKeySpecException, EmptyEmailException, IOException {

        User userWithInvalidEmail = new User("jafet", "", "123123123", mockedPhones);

        try {
            userServiceMock.registerNewUser(userWithInvalidEmail);
            fail();
        } catch (EmptyEmailException exception) {
            assertEquals("Email invalido", exception.getMessage());
        }

    }

    @Test
    @DisplayName("Should login User")
    void successfulLoginUserTest() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidPasswordException, IOException, ParseException {

        Mockito.when(userRepository.findByEmail("jafet@jafet.com"))
                .thenReturn(Optional.of(user));


        Mockito.when(hashingMethod.validatePassword(user.getPassword(), user.getPassword())).thenReturn(true);

        Mockito.when(jwtToken.createJWT(user.getEmail(), "localhost:8080", user.toString(), 100000000))
                .thenReturn(token);

        Mockito.when(userMapper.convertUserModelToDto(user)).thenReturn(mockedUserDataResponse);

        assertEquals(mockedUserDataResponse, userServiceMock.loginUser(user));
    }

    @Test
    @DisplayName("User not found in Database")
    void UserNotFoundTest() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidPasswordException, IOException, ParseException {

        Mockito.when(userRepository.findByEmail("jafet@jafet.com"))
                .thenReturn(Optional.<User>empty());

        try {
            userServiceMock.loginUser(user);
            fail();
        } catch (EntityNotFoundException exception) {
            assertEquals("Usuario nao encontrado", exception.getMessage());
        }
    }

    @Test
    @DisplayName("Invalid password for a User")
    void InvalidPasswordForAUserTest() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidPasswordException, IOException, ParseException {

        Mockito.when(userRepository.findByEmail("jafet@jafet.com"))
                .thenReturn(Optional.of(user));

        Mockito.when(hashingMethod.validatePassword(user.getPassword(), user.getPassword())).thenReturn(false);

        try {
            userServiceMock.loginUser(user);
            fail();
        } catch (InvalidPasswordException exception) {
            assertEquals("Senha incorreta", exception.getMessage());
        }
    }
}