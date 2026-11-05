package com.example.forumSP_BE.Controlers;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.forumSP_BE.Services.PostService;
import com.example.forumSP_BE.Services.SpaceService;
import com.example.forumSP_BE.Services.UserService;

import tools.jackson.databind.JsonNode;

/**
 * A REST controller that handles requests thet are related to multiple services.
 */

@RestController 
public class Controler {
    private final PostService postService;
    private final SpaceService spaceService;
    private final UserService userService;

    public Controler(PostService postService, SpaceService spaceService, UserService userService) {
        this.postService = postService;
        this.spaceService = spaceService;
        this.userService = userService;
    }
    
    /** Add a new post to selected space */
    @PostMapping("/{spaceName}/addPost")
    public ResponseEntity<?> addPost(@PathVariable String spaceName, @RequestBody JsonNode body, Principal principal) {
    if (body.get("title") == null || body.get("content") == null) {
        return ResponseEntity.badRequest().body("Missing required fields: title and/or content");
    }

    /* 
    createPost() creates a new post and returns the id of the creaded post
    linkPost() liks the post to the space
    */
    return spaceService.linkPost(
        postService.createPost(
            body.get("title").asString(),
            body.get("content").asString(),
            principal.getName()), 
        spaceName);
    }

    /** Show all post from selected space. */
    @GetMapping("/{spaceName}/getPosts")
    public ResponseEntity getPosts(@PathVariable String spaceName) {
        return postService.getPosts(spaceService.getSpace(spaceName));
    }
}