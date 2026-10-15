package com.example.forumSP_BE.Services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.forumSP_BE.Repositories.PostRepository;
import com.example.forumSP_BE.Repositories.SpaceRepository;
import com.example.forumSP_BE.Schemas.PostSchema;
import com.example.forumSP_BE.Schemas.SpaceSchema;

public class PostService {
    private final PostRepository postRepository;
    private final SpaceRepository spaceRepository;
    public PostService (PostRepository postRepository, SpaceRepository spaceRepository) {
        this.postRepository = postRepository;
        this.spaceRepository = spaceRepository;
    }

    /** Create a new post, space needs to exist. */
    public ResponseEntity createPost(String title, String content, String creator, String space) {
        
        // Form the post
        PostSchema newPost = new PostSchema();
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setAuthor(creator);

        // Check if space exists
        for (String currentSpace : spaceRepository.findAll().stream().map(SpaceSchema::getName).toArray(String[]::new)) {
            if (currentSpace.equals(space)) {
                postRepository.save(newPost);
                return ResponseEntity.ok("Post " + title + " created successfully!");
            }
        }

        // If space not exists
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Space " + space + " not found.");
    }
    
    /** Return all posts that are asocieted with a space */
    public ResponseEntity getSpacePosts(String space) {
    }

}
