package com.example.social_api.service;

import com.example.social_api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long userId);
    Page<User> getAllUsers(Pageable pageable);
    void deleteUser(Long userId);
    Optional<User> findByApiKey(String apiKey);

}
