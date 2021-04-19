package io.ninjabet.auth.controller;

import io.ninjabet.auth.entity.User;
import io.ninjabet.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping("/")
    User addUser(@RequestBody User user) {
        return this.userService.addUser(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "User already exists"));
    }
}