package com.example.siirestapi.service;

import com.example.siirestapi.exception.LectureNotFoundException;
import com.example.siirestapi.exception.UserNotFoundException;
import com.example.siirestapi.model.Lecture;
import com.example.siirestapi.model.User;
import com.example.siirestapi.repository.LectureRepository;
import com.example.siirestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Set<Lecture> getUserLectures(String login) {
        return userRepository.findByLogin(login)
                .map(User::getLectures)
                .orElseThrow(UserNotFoundException::new);

    }

    @Transactional
    public User updateUser(String login, User user) {
        User userEdited = userRepository.findByLogin(login)
                .orElseThrow(UserNotFoundException::new);

        userEdited.setEmail(user.getEmail());
        return userEdited;
    }

    @Transactional
    public User cancelBooking(String login, Long id) {
        User editedUser = userRepository.findByLogin(login)
                .orElseThrow(UserNotFoundException::new);

        Lecture editedLecture = lectureRepository.findById(id)
                .orElseThrow(LectureNotFoundException::new);

        editedUser.removeLectureToUser(editedLecture);
        return editedUser;
    }
}
