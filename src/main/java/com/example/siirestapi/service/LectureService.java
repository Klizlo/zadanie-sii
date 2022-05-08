package com.example.siirestapi.service;

import com.example.siirestapi.model.Lecture;
import com.example.siirestapi.model.User;
import com.example.siirestapi.repository.LectureRepository;
import com.example.siirestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    public List<Lecture> getAllLectures(){
        return lectureRepository.findAll();
    }
    //add user to lecture
    @Transactional
    public ResponseEntity<String> addUserToLecture(Long id, User user){
        Lecture lecture = lectureRepository.getById(id);

        if (lecture.getUsers().size() == 5){
            //if lecture is full, send a message
            return ResponseEntity.badRequest().body("Brak wolnych miejsc na prelekcje");
        }

        User foundUser = userRepository.findByLogin(user.getLogin());

        if(foundUser == null){
            //if found user is null, create new user
            foundUser = userRepository.save(user);
        }else if (!foundUser.getEmail().equals(user.getEmail())){
            //if user entered bad email, send the message
            return ResponseEntity.badRequest().body("Podany login jest już zajęty");
        }else if (foundUser.checkIfUserIsRegisteredAt(lecture.getStart())){
            //if the user is already signed up for another lecture at that time, send the message
            return ResponseEntity.badRequest().body("O tej godzinie jesteś już zapisany na inną prelekcję");
        }

        //write message to file powiadomienia.txt
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/powiadomienia.txt", true));
            bufferedWriter.write("date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n");
            bufferedWriter.write("to: " + foundUser.getEmail() + "\n");
            bufferedWriter.write("contents: Pomyślnie zapisałeś się na prelekcję pt. " + lecture.getName()
                    + ", która odbędzie się dnia " + lecture.getStart().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    + " o godzinie " + lecture.getStart().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

        lecture.addUserToLecture(foundUser);
        return ResponseEntity.ok().body("Pomyślnie zapisałeś się na prelekcję.");
    }

}
