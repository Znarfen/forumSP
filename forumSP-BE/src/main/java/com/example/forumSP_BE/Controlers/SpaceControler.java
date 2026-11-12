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
public class SpaceControler {
    private final SpaceService spaceService;

    public SpaceControler(SpaceService spaceService, PostService postService) {this.spaceService = spaceService;}
    
    @PostMapping("/addSpace")
    public ResponseEntity<?> addSpace(@RequestBody JsonNode body, Principal principal) {
        if (body.get("name") == null || body.get("description") == null) {
            return ResponseEntity.badRequest().body("Missing required fields: name and/or description");
        }
        JsonNode tagsNode = body.get("tags");
        String[] tags = null;
        if (tagsNode != null && tagsNode.isArray()) {
            tags = new String[tagsNode.size()];
            for (int i = 0; i < tagsNode.size(); i++) {
                tags[i] = tagsNode.get(i).asString();
            }
        }
        return spaceService.createSpace(body.get("name").asString(), body.get("description").asString(), tags, principal.getName());
    }

    @GetMapping("/spaces")
    public ResponseEntity getAllSpaces() {
        return spaceService.getSpaces();
    }

    @GetMapping ("/spaces/names")
    public ResponseEntity getAllSpacesNames() {
        return spaceService.getSpacesNames();
    }

    @GetMapping ("/{spaceName}")
    public ResponseEntity getSpace(@PathVariable String spaceName) {
        return spaceService.getSpaceName(spaceName);
    }

    @GetMapping ("/getSuggestedSpaces")
    public ResponseEntity getSuggestedSpaces(@RequestBody JsonNode body) {
        return spaceService.getMatchingSpaces(body.get("description").asString());
    }

}
