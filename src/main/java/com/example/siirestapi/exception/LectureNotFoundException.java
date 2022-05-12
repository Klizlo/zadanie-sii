package com.example.siirestapi.exception;

public class LectureNotFoundException extends RuntimeException{

    public LectureNotFoundException() {
        super("Podana prelekcja nie istnieje");
    }
}
