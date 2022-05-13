package com.example.siirestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private LocalDateTime start;

    private LocalDateTime end;

    @JsonIgnore
    @ManyToMany(mappedBy = "lectures")
    private Set<User> users = new HashSet<>();

    public void signIn(User user){
        users.add(user);
        user.getLectures().add(this);
    }

    public void signOut(User user){
        users.remove(user);
        user.getLectures().remove(this);
    }
}
