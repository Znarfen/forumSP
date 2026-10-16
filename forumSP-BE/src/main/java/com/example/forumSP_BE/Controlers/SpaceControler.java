package com.example.forumSP_BE.Controlers;

import java.security.Principal;

import javax.tools.JavaFileObject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.forumSP_BE.Schemas.PostSchema;
import com.example.forumSP_BE.Services.SpaceService;

import tools.jackson.databind.JsonNode;

@RestController 
public class SpaceControler {
    private final SpaceService spaceService;

    public SpaceControler(SpaceService spaceService) {this.spaceService = spaceService;}
    
    @PostMapping("/addSpace")
    public ResponseEntity<?> addSpace(@RequestBody JsonNode body, Principal principal) {
        if (body.get("name") == null || body.get("description") == null) {
            return ResponseEntity.badRequest().body("Missing required fields: name and/or description");
        }
        return spaceService.createSpace(body.get("name").asString(), body.get("description").asString(), principal.getName());
    }

    @PostMapping("/{spaceName}/addPost")
    public ResponseEntity<?> addPost(@PathVariable String spaceName, @RequestBody JsonNode body, Principal principal) {
        if (body.get("title") == null || body.get("content") == null) {
            return ResponseEntity.badRequest().body("Missing required fields: title and/or content");
        }
        return spaceService.createPost(body.get("title").asString(), body.get("content").asString(), principal.getName(), spaceName);
    }

    @GetMapping("/getAllSpaces")
    public Iterable<com.example.forumSP_BE.Schemas.SpaceSchema> getAllSpaces() {
        return spaceService.getSpace();
    }

    @GetMapping ("/getAllSpaces/names")
    public String[] getAllSpacesNames() {
        return spaceService.getSpacesNames();
    }

    @GetMapping("/{spaceName}/getPosts")
    public ResponseEntity getPosts(@PathVariable String spaceName) {
        return spaceService.getPosts(spaceName);
    }

    @GetMapping ("/{spaceName}")
    public com.example.forumSP_BE.Schemas.SpaceSchema getSpace(@PathVariable String spaceName) {
        return spaceService.getSpace(spaceName);
    }

}
