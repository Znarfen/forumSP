package com.example.forumSP_BE.Services;

import com.example.forumSP_BE.Repositories.PostRepository;
import com.example.forumSP_BE.Repositories.SpaceRepository;
import com.example.forumSP_BE.Schemas.PostSchema;
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
    private final PostRepository postRepository;
    public SpaceService(SpaceRepository spaceRepository, PostRepository postRepository) {
        this.spaceRepository = spaceRepository;
        this.postRepository = postRepository;
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
        newSpace.setOwner(owner);
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

    /** Create a new post, space needs to exist. */
    public ResponseEntity createPost(String title, String content, String creator, String spaceName) {
        
        PostSchema newPost = new PostSchema();
        SpaceSchema space = getSpace(spaceName);
        if (space == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No space with name " + spaceName + " exists.");
        
        // Form the post
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setAuthor(creator);
        postRepository.save(newPost);

        // Save to space
        space.addPost(newPost.getId());
        spaceRepository.save(space);

        return ResponseEntity.ok("Post created successfully!");
    }

    /** Gett all posts from a space */
    public ResponseEntity getPosts(String spaceName) {

        SpaceSchema space = getSpace(spaceName);
        if (space == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No space with name " + spaceName + " exists.");

        List<PostSchema> posts = new ArrayList<>();

        for (int postId : space.getPosts()) {
            PostSchema post = postRepository.findById(postId).orElse(null);
            if (post != null) posts.add(post);
        }

        return ResponseEntity.ok(posts);
    }

    /** From a description returns a list of fitting spaces */
    public ResponseEntity getMatchingSpaces(String description) {
        System.out.println("Searching for spaces matching description: " + description);

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

        System.out.println("Points: " + points[0]);

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
                finalList.add(Map.of("space", spaces.get(i), "points", points[i], "maxPoints", maxPoints[i], "score", score));
            }
        }
        
        // Return final list with points
        if (finalList.isEmpty()) return ResponseEntity.ok(spaces.get(0)); 
        return ResponseEntity.ok(finalList);
    }
}
