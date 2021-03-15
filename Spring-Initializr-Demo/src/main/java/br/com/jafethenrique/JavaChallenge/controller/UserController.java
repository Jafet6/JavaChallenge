package br.com.jafethenrique.JavaChallenge.controller;

import br.com.jafethenrique.JavaChallenge.responses.UserDTO;
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


    @PostMapping(path = "/login")
    public ResponseEntity loginUser(@RequestBody UserDTO userDto) throws NoSuchAlgorithmException, ParseException, InvalidPasswordException, InvalidKeySpecException, JsonMappingException, JsonGenerationException, IOException {
        User user = userMapper.convertUserDTOToEntity(userDto);
        UserDTO serviceResponse = userService.loginUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(serviceResponse);
    }

    @PostMapping(path = "/register")
    public ResponseEntity registerNewUser(@RequestBody UserDTO userDto) throws NoSuchAlgorithmException, EmptyEmailException, ParseException, InvalidKeySpecException, IOException {
            User user = userMapper.convertUserDTOToEntity(userDto);
            UserDTO serviceResponse = userService.registerNewUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(serviceResponse);
    }

}
