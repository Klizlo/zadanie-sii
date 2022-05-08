package com.example.siirestapi.controller;

import com.example.siirestapi.model.Lecture;
import com.example.siirestapi.model.User;
import com.example.siirestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{login}/lectures")
    public Set<Lecture> getUserLectures(@PathVariable String login){
        return userService.getUserLectures(login);
    }

}
