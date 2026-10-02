package com.example.forumSP_BE.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.forumSP_BE.Schemas.SpaceSchema;

public interface SpaceRepository extends JpaRepository<SpaceSchema, Integer> {}
