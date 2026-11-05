package com.example.forumSP_BE.Services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.forumSP_BE.Repositories.PostRepository;
import com.example.forumSP_BE.Schemas.PostSchema;
import com.example.forumSP_BE.Schemas.SpaceSchema;

@Service
public class PostService {
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /** Get specific post from id */
    public ResponseEntity getPost(Long id) {
        PostSchema post = postRepository.findById(id.intValue()).orElse(null);
        if (post == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(post);
    }

    /** Get all posts form space */
    public ResponseEntity getPosts(SpaceSchema space) {
        PostSchema[] posts = new PostSchema[space.getPosts().length];
        PostSchema post;

        for (int i = 0; i < posts.length; i++) {
            post = postRepository.findById(space.getPost(i).intValue()).orElse(null);
            if (post != null) posts[i] = post;
        }
        
        return ResponseEntity.ok(posts);
    }

    /** Create a new post and return id */
    public Long createPost(String title, String content, String creator) {
        PostSchema newPost = new PostSchema();
        
        // Form the post
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setAuthor(creator);
        postRepository.save(newPost);

        return newPost.getId();
    }

    /** Remove a post by id. If check is false, it will skip the check for author */
    public ResponseEntity removePost(int id, String user, boolean check) {
        PostSchema post = postRepository.findById(id).orElse(null);
        
        if (check) {
            if (post == null) return ResponseEntity.notFound().build();
            if (!post.getAuthor().equals(user)) return ResponseEntity.status(403).body("You have no permission to delete this post.");
        }

        postRepository.delete(post);
        return ResponseEntity.ok("Post '" + post.getTitle() + "' deleted successfuly.");
    }
    /** Remove a post by id with check for author */
    public ResponseEntity removePost(int id, String user) {return removePost(id, user, true);}
    /** Remove a post by id without check for author */
    public ResponseEntity removePost(int id) {return removePost(id, "", false);}

    /** Remove all posts in a space */
    public ResponseEntity removeAllPostsInSpace(SpaceSchema space, String user) {
        if (space == null) return ResponseEntity.status(404).body("Space not found!");
        if (!space.getAdmin().equals(user)) return ResponseEntity.status(403).body("You do not have permintion to remove all posts from this space.");
        for (Long id : space.getPosts()) postRepository.deleteById(id.intValue());
        return ResponseEntity.ok("All posts in space '" + space.getName() + "' was removed successfuly.");
    }
}