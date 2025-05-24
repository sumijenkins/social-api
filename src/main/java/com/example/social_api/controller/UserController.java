package com.example.social_api.controller;

import com.example.social_api.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<User> getAllUsers(Pageable pageable){
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long userId){ //response entity for returning http answer
        Optional<User> userOpt = userService.getUserById(userId);
        return userOpt.map(ResponseEntity::ok) //return http 200 OK + user
                .orElseGet(()-> ResponseEntity.notFound().build()); //return http 404
    }

    @PostMapping("/{id}")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build(); //HTTP 204 No Content
    }
}
