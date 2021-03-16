package br.com.jafethenrique.JavaChallenge.controller;

import br.com.jafethenrique.JavaChallenge.requests.UserLoginRequest;
import br.com.jafethenrique.JavaChallenge.requests.UserRegisterRequest;
import br.com.jafethenrique.JavaChallenge.responses.UserDataResponse;
import br.com.jafethenrique.JavaChallenge.mappers.UserMapper;
import br.com.jafethenrique.JavaChallenge.domain.User;
import br.com.jafethenrique.JavaChallenge.service.UserService;
import br.com.jafethenrique.JavaChallenge.exceptions.EmptyEmailException;
import br.com.jafethenrique.JavaChallenge.exceptions.InvalidPasswordException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/register")
    public ResponseEntity registerNewUser(@RequestBody UserRegisterRequest user) throws NoSuchAlgorithmException, EmptyEmailException, ParseException, InvalidKeySpecException, IOException {
        User userDomain = userMapper.convertUserRegisterRequestToEntity(user);
        UserDataResponse serviceResponse = userService.registerNewUser(userDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceResponse);
    }

    @PostMapping(path = "/login")
    public ResponseEntity loginUser(@RequestBody UserLoginRequest userDataResponse) throws NoSuchAlgorithmException, ParseException, InvalidPasswordException, InvalidKeySpecException, JsonMappingException, JsonGenerationException, IOException {
        User userDomain = userMapper.convertUserLoginRequestToEntity(userDataResponse);
        UserDataResponse serviceResponse = userService.loginUser(userDomain);
        return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
    }

}
