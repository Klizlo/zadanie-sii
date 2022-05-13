package com.example.siirestapi.service;

import com.example.siirestapi.controller.dto.StatementDto;
import com.example.siirestapi.controller.dto.StatementRecordHourDto;
import com.example.siirestapi.controller.dto.StatementRecordLecturesDto;
import com.example.siirestapi.exception.InvalidRegistrationToLectureException;
import com.example.siirestapi.model.Lecture;
import com.example.siirestapi.model.User;
import com.example.siirestapi.repository.LectureRepository;
import com.example.siirestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        lecture.signIn(foundUser);
        return foundUser;
    }

    public StatementDto getLectureStatement() {

        List<Lecture> lectures = lectureRepository.findAll();

        long sumOfParticipants = lectures.stream()
                .map(Lecture::getUsers)
                .flatMap(Set::stream)
                .distinct()
                .count();

        Set<LocalDateTime> localDateTimes = lectures.stream()
                .map(Lecture::getStart)
                .collect(Collectors.toSet());

        StatementRecordHourDto statementRecordHourDto = new StatementRecordHourDto();

        for (LocalDateTime time : localDateTimes){
            float percentageOfParticipantsByHour = (float) lectures.stream()
                    .filter(l -> l.getStart().equals(time))
                    .map(Lecture::getUsers)
                    .mapToLong(Set::size)
                    .sum() / sumOfParticipants;
            statementRecordHourDto.getPercentOfUsersByHour().put(time, percentageOfParticipantsByHour);
        }

        StatementRecordLecturesDto statementRecordLecturesDto = new StatementRecordLecturesDto();

        for (Lecture lecture: lectures) {
            float percentageOfParticipantsByLecture = (float) lecture.getUsers().size() / sumOfParticipants;
            statementRecordLecturesDto.getPercentOfUsersByLectureName()
                    .put(lecture.getName(), percentageOfParticipantsByLecture);
        }

        return StatementDto.builder()
                .byHour(statementRecordHourDto)
                .byLecture(statementRecordLecturesDto)
                .build();
    }
}
