package com.example.forumSP_BE.Services;

import com.example.forumSP_BE.Repositories.PostRepository;
import com.example.forumSP_BE.Repositories.SpaceRepository;
import com.example.forumSP_BE.Schemas.SpaceSchema;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service 
public class SpaceService {
    private final SpaceRepository spaceRepository;
    public SpaceService(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    /** Create a new space */
    public ResponseEntity createSpace(String name, String description, String[] tags, String owner) {
        //System.out.println("Creating space: " + name + ", description: " + description + ", owner: " + owner);

        // Check if space already exists
        if (spaceRepository.findAll().stream().anyMatch(space -> space.getName().equals(name.toLowerCase()))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Space already exists");
        }

        // Create new space
        com.example.forumSP_BE.Schemas.SpaceSchema newSpace = new com.example.forumSP_BE.Schemas.SpaceSchema();
        newSpace.setName(name.toLowerCase());
        newSpace.setDescription(description);
        newSpace.setAdmin(owner);
        newSpace.setTags(tags);
        
        spaceRepository.save(newSpace);
        return ResponseEntity.ok("Space " + name + " created successfully!");
    }

    /** Get all spaces */
    public Iterable<SpaceSchema> getSpace() {
        return spaceRepository.findAll();
    }

    /** Get a specific space by name */
    public SpaceSchema getSpace(String spaceName) {
        return spaceRepository.findAll().stream()
                .filter(s -> s.getName().equals(spaceName.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    /** Get all space names */
    public String[] getSpacesNames() {
        return spaceRepository.findAll().stream()
                .map(SpaceSchema::getName)
                .toArray(String[]::new);
    }

    /** From a description returns a list of fitting spaces (where the firs one has the best match). */
    public ResponseEntity getMatchingSpaces(String description) {
        // System.out.println("Searching for spaces matching description: " + description);

        List<SpaceSchema> spaces = spaceRepository.findAll();
        List<Map<String, Object>> finalList = new ArrayList<>();

        String desc = description.toLowerCase();
        int[] points = new int[spaceRepository.findAll().size()];
        int[] maxPoints = new int[spaceRepository.findAll().size()];
        float score = 0.00f;

        // Determinate points for each space based on tags
        for (int i = 0; i < spaces.size(); i++) {
            for (String tag : spaces.get(i).getTags()) {
                maxPoints[i]++;
                if (desc.contains(tag)) points[i]++;
            }
        }

        // If no space do not have any points, return a not found
        int max = 0;
        for (int i : points) if (i > max) max = i;
        if (max == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No fitting space was found for your post.");

        // Sort spaces based on score
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] < points[j]) {
                    int temp = points[i];
                    points[i] = points[j];
                    points[j] = temp;

                    SpaceSchema tempSpace = spaces.get(i);
                    spaces.set(i, spaces.get(j));
                    spaces.set(j, tempSpace);
                }
            }
        }

        // Remove spaces with less than 20% of hits
        for (int i = 0; i < points.length; i++) {
            score = (float) points[i] / (float) maxPoints[i];
            if (score > 0.2f) {
                finalList.add(Map.of("space", spaces.get(i), ".points", points[i], ".maxPoints", maxPoints[i], ".score", score));
            }
        }
        
        // Return final list with points
        if (finalList.isEmpty()) return ResponseEntity.ok(spaces.get(0)); 
        return ResponseEntity.ok(finalList);
    }

    /** Link a post to a space */
    public ResponseEntity linkPost(Long postId, String spaceName) {
        SpaceSchema space = getSpace(spaceName);
        if (space == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Space, '" + spaceName + "' not found!");
        space.addPost(postId);
        spaceRepository.save(space);
        return ResponseEntity.ok("Post added to space '" + spaceName + "' successfully!");
    }

    /** Get all posts from a space */
    public ResponseEntity getPosts(String spaceName) {
        var space = spaceRepository.findAll().stream()
                .filter(s -> s.getName().equals(spaceName.toLowerCase()))
                .findFirst()
                .orElse(null);
        if (space == null) {
            return ResponseEntity.notFound().build();
        }
        /* 
        var posts = postRepository.findAll().stream()
                .filter(p -> p.getBelonging().equals(spaceName.toLowerCase()))
                .toList();
        return ResponseEntity.ok(posts);
        */

        return ResponseEntity.ok("");
    }
}
