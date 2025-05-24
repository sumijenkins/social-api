package com.example.social_api.repository;

import com.example.social_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByApiKey(String apiKey);
}
