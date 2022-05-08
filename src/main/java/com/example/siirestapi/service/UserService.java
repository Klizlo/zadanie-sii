package com.example.siirestapi.service;

import com.example.siirestapi.model.Lecture;
import com.example.siirestapi.model.User;
import com.example.siirestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    public Set<Lecture> getUserLectures(String login) {
        User user = userRepository.findByLogin(login);
        if (user == null){
            return new HashSet<>();
        }
        return user.getLectures();
    }

}
