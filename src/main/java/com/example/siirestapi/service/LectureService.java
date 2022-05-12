package com.example.siirestapi.service;

import com.example.siirestapi.exception.InvalidRegistrationToLectureException;
import com.example.siirestapi.model.Lecture;
import com.example.siirestapi.model.User;
import com.example.siirestapi.repository.LectureRepository;
import com.example.siirestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;

    private final EmailService emailService;

    public List<Lecture> getAllLectures(){
        return lectureRepository.findAll();
    }
    //add user to lecture
    @Transactional
    public User addUserToLecture(Long id, User user){
        Lecture lecture = lectureRepository.getById(id);

        if (lecture.getUsers().size() == 5){
            //if lecture is full, throw an exception
            throw new InvalidRegistrationToLectureException("Podana prelekcja nie ma już wolnych miejsc");
        }

        User foundUser = userRepository.findByLogin(user.getLogin())
                .orElseGet(() -> userRepository.save(user));

        if (foundUser.getLogin().equals(user.getLogin()) && !foundUser.getEmail().equals(user.getEmail())){
            //if user entered bad email, throw an exception
            throw new InvalidRegistrationToLectureException("Podany login jest już zajęty");
        }

        if (foundUser.checkIfUserIsRegisteredAt(lecture.getStart())){
            //if the user is already signed up for another lecture at that time, throw an exception
            throw new InvalidRegistrationToLectureException("Użytkownik jest już zapisany na prelekcję o tej godzinie");
        }

        emailService.sendEmail(foundUser, lecture);

        lecture.addUserToLecture(foundUser);
        return foundUser;
    }

}
