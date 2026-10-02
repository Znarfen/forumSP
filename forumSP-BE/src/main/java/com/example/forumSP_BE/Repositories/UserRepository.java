package com.example.forumSP_BE.Repositories;

import com.example.forumSP_BE.Schemas.UserSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserSchema, Integer> {
    Optional<UserSchema> findByName(String name);
}