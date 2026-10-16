package com.example.forumSP_BE.Services;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.forumSP_BE.Repositories.UserRepository;
import com.example.forumSP_BE.Schemas.PostSchema;
import com.example.forumSP_BE.Schemas.UserSchema;
import org.springframework.security.core.userdetails.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostSchema postSchema;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PostSchema postSchema) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.postSchema = postSchema;
    }

    /** Register new user */
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

    /** Get users */
    public Iterable<UserSchema> getUsers(String username) {
        UserSchema currentUser = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found: " + username));

        return java.util.Collections.singletonList(userRepository.findByName(currentUser.getName()).get());
    }

    /** Spring Security uses this method during authentication */
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

    /** Remove (dis- and) like from user */
    

    /** Add a like to a post */
    public ResponseEntity addLike(String username, int postId, Boolean like) {

        UserSchema currentUser = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found: " + username));

        int[] liked = currentUser.getLikedPosts();
        int[] disliked = currentUser.getDislikedPosts();

        for (int[] i : new int[][] {liked, disliked}) {
            
        }


        return ResponseEntity.ok("fuck you");
    }
}