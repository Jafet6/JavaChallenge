package br.com.jafethenrique.JavaChallenge.user;

import br.com.jafethenrique.JavaChallenge.utils.exceptions.InvalidPasswordException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

//    @GetMapping
//    public List<User> getStudents() {
//
//        return studentService.getStudents();
//    }


    @PostMapping(path = "/login")
    public ResponseEntity loginUser(@RequestBody User user) throws NoSuchAlgorithmException, InvalidPasswordException, InvalidKeySpecException {
            String serviceResponse = userService.loginUser(user);
            JSONObject responseObject = new JSONObject();
            responseObject.put("response", serviceResponse);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @PostMapping(path = "/register")
    public ResponseEntity registerNewUser(@RequestBody User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
            String serviceResponse = userService.registerNewUser(user);
            JSONObject responseObject = new JSONObject();
            responseObject.put("response", serviceResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }

}
