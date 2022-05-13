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
    @JoinTable(name = "user_lectures",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lecture_id"))
    private Set<Lecture> lectures = new HashSet<>();

    public void signIn(Lecture lecture) {
        lectures.add(lecture);
        lecture.getUsers().add(this);
    }

    public void signOut(Lecture lecture) {
        lectures.remove(lecture);
        lecture.getUsers().remove(this);
    }

    public boolean checkIfUserIsRegisteredAt(LocalDateTime start) {
        return lectures.stream()
                .anyMatch(lecture -> lecture.getStart().equals(start));
    }
}
