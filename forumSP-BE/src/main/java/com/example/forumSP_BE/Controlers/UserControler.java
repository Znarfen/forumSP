package com.example.forumSP_BE.Controlers;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.forumSP_BE.Schemas.UserSchema;
import com.example.forumSP_BE.Services.UserService;

@RestController 
public class UserControler {
    private final UserService userService;
    
    public UserControler(UserService userService) {this.userService = userService;}

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserSchema user) {
        // System.out.println("controller: " + user.getName());
        return userService.registerUser(user.getName(), user.getPassword());
    }

    @GetMapping("/users")
    public Iterable<UserSchema> getUsers(Principal principal) {
        return userService.getUsers(principal.getName());
    }
}
