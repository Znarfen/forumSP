package com.example.forumSP_BE.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.forumSP_BE.Repositories.PostRepository;
import com.example.forumSP_BE.Repositories.SpaceRepository;
import com.example.forumSP_BE.Schemas.PostSchema;
import com.example.forumSP_BE.Schemas.SpaceSchema;

@Service 
public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final PostRepository postRepository;
    public SpaceService(SpaceRepository spaceRepository, PostRepository postRepository) {
        this.spaceRepository = spaceRepository;
        this.postRepository = postRepository;
    }

    /** Create a new space */
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

        return ResponseEntity.ok("Post creatd sucesfuly!");
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

}
