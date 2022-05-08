package com.example.siirestapi.controller;

import com.example.siirestapi.model.User;
import com.example.siirestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{login}/lectures")
    public ResponseEntity<?> getUserLectures(@PathVariable String login){
        return userService.getUserLectures(login);
    }

    @PutMapping("users/{login}")
    public ResponseEntity<String> changeEmail(@PathVariable String login, @RequestBody User user){
        return userService.changeEmail(login, user);
    }

    @PutMapping("/users/{login}/lectures/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable String login, @PathVariable Long id){
        return userService.cancelBooking(login, id);
    }
}
