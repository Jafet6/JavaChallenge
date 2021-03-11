package br.com.jafethenrique.JavaChallenge.user;

import br.com.jafethenrique.JavaChallenge.utils.exceptions.InvalidPasswordException;
import br.com.jafethenrique.JavaChallenge.utils.hashingMethod.HashingMethod;
import br.com.jafethenrique.JavaChallenge.utils.jwtToken.JWTToken;
import br.com.jafethenrique.JavaChallenge.utils.phoneObject.Phones;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTToken jwtToken;

    @Mock
    private HashingMethod hashingMethod;

    @InjectMocks
    private UserService userServiceMock;

    @Test
    @DisplayName("Should register a new User")
    void registerNewUserTest() throws NoSuchAlgorithmException, InvalidKeySpecException{
//        UserService userServiceMock = new UserService(jwtToken, userRepository);
        Phones phonesMocked = new Phones("123123123", "31");

        ArrayList listOfPhones = new ArrayList();
        listOfPhones.add(phonesMocked);

        UserModel mockedUser = new UserModel("jafet", "jafet@jafet.com", "123123123", listOfPhones);

        Mockito.when(userRepository.findByEmail("jafet@jafet.copm"))
                .thenReturn(Optional.of(mockedUser));

        String token = "asdfgasdfasf";
        Mockito.when(jwtToken.createJWT(mockedUser.getEmail(), "localhost:8080", mockedUser.toString(), 100000000))
                .thenReturn(token);

        assertEquals(token, userServiceMock.registerNewUser(mockedUser));
    }

	@Test
    @DisplayName("Should login a User")
	public void loginUserTest() throws NoSuchAlgorithmException, InvalidPasswordException, InvalidKeySpecException {
//        UserService userServiceMock = new UserService(jwtToken, userRepository);
        Phones phonesMocked = new Phones("123123123", "31");

        ArrayList listOfPhones = new ArrayList();
        listOfPhones.add(phonesMocked);

		UserModel mockedUser = new UserModel("jafet", "jafet@jafet.com", "123123123", listOfPhones);
        UserRequestModel userRequestModel = new UserRequestModel("jafet@jafet.com", "123123123");

		Mockito.when(userRepository.findByEmail("jafet@jafet.com")).thenReturn(Optional.of(mockedUser));

	    Mockito.when(hashingMethod.validatePassword(mockedUser.getPassword(), mockedUser.getPassword())).thenReturn(true);

        String token = "asdfgasdfasf";
        Mockito.when(jwtToken.createJWT(mockedUser.getEmail(), "localhost:8080", mockedUser.toString(), 100000000)).thenReturn(token);

		assertEquals(token, userServiceMock.loginUser(userRequestModel));

	}
}