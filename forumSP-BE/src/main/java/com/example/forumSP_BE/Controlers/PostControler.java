package com.example.forumSP_BE.Controlers;

import java.security.Principal;

import javax.tools.JavaFileObject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.forumSP_BE.Services.*;

import tools.jackson.databind.JsonNode;

@RestController 
public class PostControler {
    private final PostService postService;

    public PostControler(PostService postService) {this.postService = postService;}
}
