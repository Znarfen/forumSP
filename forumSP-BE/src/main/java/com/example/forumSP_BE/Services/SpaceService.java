package com.example.forumSP_BE.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.forumSP_BE.Repositories.SpaceRepository;
import com.example.forumSP_BE.Schemas.PostSchema;
import com.example.forumSP_BE.Schemas.SpaceSchema;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service 
public class SpaceService {
    private final SpaceRepository spaceRepository;
    public SpaceService(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    // Create a new space
    public ResponseEntity createSpace(String name, String description, String owner) {
        //System.out.println("Creating space: " + name + ", description: " + description + ", owner: " + owner);

        // Check if space already exists
        if (spaceRepository.findAll().stream().anyMatch(space -> space.getName().equals(name.toLowerCase()))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Space already exists");
        }

        // Create new space
        com.example.forumSP_BE.Schemas.SpaceSchema newSpace = new com.example.forumSP_BE.Schemas.SpaceSchema();
        newSpace.setName(name.toLowerCase());
        newSpace.setDescription(description);
        newSpace.setOwner(owner);
        
        spaceRepository.save(newSpace);
        return ResponseEntity.ok("Space " + name + " created successfully!");
    }

    // Get all spaces
    public Iterable<SpaceSchema> getSpaces() {
        return spaceRepository.findAll();
    }

    // Get a specific space by name
    public SpaceSchema getSpace(String spaceName) {
        return spaceRepository.findAll().stream()
                .filter(s -> s.getName().equals(spaceName.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    // Get all space names
    public String[] getSpacesNames() {
        return spaceRepository.findAll().stream()
                .map(SpaceSchema::getName)
                .toArray(String[]::new);
    }
}
