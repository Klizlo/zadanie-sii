package com.example.siirestapi.service;

import com.example.siirestapi.model.Lecture;
import com.example.siirestapi.model.User;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    public void sendEmail(User user, Lecture lecture){

        //write message to file powiadomienia.txt
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/powiadomienia.txt", true))) {
            bufferedWriter.write("date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n");
            bufferedWriter.write("to: " + user.getEmail() + "\n");
            bufferedWriter.write("contents: Pomyślnie zapisałeś się na prelekcję pt. " + lecture.getName()
                    + ", która odbędzie się dnia " + lecture.getStart().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    + " o godzinie " + lecture.getStart().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n\n");
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

}
