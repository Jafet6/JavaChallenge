package br.com.jafethenrique.JavaChallenge.user;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity loginUser(@RequestBody User user) {
            String serviceResponse = userService.findUserByEmail(user);
            JSONObject responseObject = new JSONObject();
            responseObject.put("response", serviceResponse);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @PostMapping(path = "/register")
    public ResponseEntity registerNewUser(@RequestBody User user) {
            String serviceResponse = userService.addNewUser(user);
            JSONObject responseObject = new JSONObject();
            responseObject.put("response", serviceResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseObject);
    }

}
