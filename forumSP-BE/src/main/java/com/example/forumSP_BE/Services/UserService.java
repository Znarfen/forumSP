package com.example.forumSP_BE.Services;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.forumSP_BE.Repositories.UserRepository;
import com.example.forumSP_BE.Schemas.UserSchema;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register new user
    public ResponseEntity<String> registerUser(String username, String password) {
        
        // Check if user already exists
        if (userRepository.findByName(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        // Create new user
        UserSchema newUser = new UserSchema();
        newUser.setName(username);
        newUser.setPassword(passwordEncoder.encode(password));
        
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    // Get all users
    public Iterable<UserSchema> getUsers(String username) {
        UserSchema currentUser = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found: " + username));

        // If you have role 'USER', canyou only see yourself.
        return java.util.Collections.singletonList(userRepository.findByName(currentUser.getName()).get());
    }

    // Spring Security uses this method during authentication
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserSchema user = userRepository.findByName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username));

        return User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .build();
    }
}