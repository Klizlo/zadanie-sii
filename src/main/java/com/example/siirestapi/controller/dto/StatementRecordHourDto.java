package com.example.siirestapi.controller.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
public class StatementRecordHourDto {

    private Map<LocalDateTime, Float> percentOfUsersByHour;

    public StatementRecordHourDto() {
        this.percentOfUsersByHour = new HashMap<>();
    }
}
