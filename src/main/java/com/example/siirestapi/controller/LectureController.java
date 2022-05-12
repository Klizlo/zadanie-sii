package com.example.siirestapi.controller;

import com.example.siirestapi.model.Lecture;
import com.example.siirestapi.model.User;
import com.example.siirestapi.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @GetMapping("/lectures")
    public List<Lecture> getAllLectures(){
        return lectureService.getAllLectures();
    }

    @PutMapping("/lectures/{id}/users")
    public User addUserToLecture(@PathVariable Long id, @RequestBody User user){
        return lectureService.addUserToLecture(id, user);
    }

}

