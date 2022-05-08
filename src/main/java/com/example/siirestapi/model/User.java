package com.example.siirestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_has_lecture",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "lecture_id"))
    private Set<Lecture> lectures = new HashSet<>();

    public void addLectureToUser(Lecture lecture){
        lectures.add(lecture);
        lecture.getUsers().add(this);
    }

    public void removeLectureToUser(Lecture lecture){
        lectures.remove(lecture);
        lecture.getUsers().remove(this);
    }

    public boolean checkIfUserIsRegisteredAt(LocalDateTime start) {
        for (Lecture l: lectures){
            if (l.getStart().equals(start)){
                return true;
            }
        }
        return false;
    }
}