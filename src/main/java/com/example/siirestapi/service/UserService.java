package com.example.siirestapi.service;

import com.example.siirestapi.model.Lecture;
import com.example.siirestapi.model.User;
import com.example.siirestapi.repository.LectureRepository;
import com.example.siirestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public ResponseEntity<?> getUserLectures(String login) {
        User user = userRepository.findByLogin(login);
        if (user == null){
            return ResponseEntity.badRequest().body("Użytkownik o takim loginie nie istnieje");
        }
        return ResponseEntity.ok().body(user.getLectures());
    }

    @Transactional
    public ResponseEntity<String> changeEmail(String login, User user){
        User userEdited = userRepository.findByLogin(login);
        if(userEdited == null){
            return ResponseEntity.badRequest().body("Użytkownik o podanym loginie nie istnieje");
        }
        userEdited.setEmail(user.getEmail());
        return ResponseEntity.ok().body("Poprawnie zmieniono adres email");
    }

    @Transactional
    public ResponseEntity<String> cancelBooking(String login, Long id) {
        User editedUser = userRepository.findByLogin(login);
        if (editedUser == null){
            return ResponseEntity.badRequest().body("Użytkownik o podanym loginie nie istnieje");
        }
        Lecture editedLecture = lectureRepository.findById(id).orElse(null);
        if (editedLecture == null){
            return ResponseEntity.badRequest().body("Nie istnieje taka prelekcja");
        }
        editedUser.removeLectureToUser(editedLecture);
        return ResponseEntity.ok().body("Pomyślnie zostałeś wypisany z prelekcji");
    }
}
