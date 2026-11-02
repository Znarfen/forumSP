package com.example.forumSP_BE.Services;

import org.springframework.stereotype.Service;
import com.example.forumSP_BE.Repositories.PostRepository;
import com.example.forumSP_BE.Repositories.SpaceRepository;

@Service
class PostService {
    private final SpaceRepository spaceRepository;
    private final PostRepository postRepository;
    public PostService(SpaceRepository spaceRepository, PostRepository postRepository) {
        this.spaceRepository = spaceRepository;
        this.postRepository = postRepository;
    }

    

    
}