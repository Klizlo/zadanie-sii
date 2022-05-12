package com.example.siirestapi.controller.dto;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class StatementRecordLecturesDto {

    private Map<String, Float> percentOfUsersByLectureName;

    public StatementRecordLecturesDto(){
        this.percentOfUsersByLectureName = new HashMap<>();
    }

}
