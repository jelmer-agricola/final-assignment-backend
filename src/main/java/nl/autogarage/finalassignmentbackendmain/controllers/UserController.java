package nl.autogarage.finalassignmentbackendmain.controllers;
import nl.autogarage.finalassignmentbackendmain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


}
