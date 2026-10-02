package com.example.forumSP_BE.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.forumSP_BE.Schemas.PostSchema;

@Repository
public interface PostRepository extends JpaRepository<PostSchema, Integer> {}