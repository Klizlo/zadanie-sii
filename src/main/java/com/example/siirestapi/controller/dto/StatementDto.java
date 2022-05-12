package com.example.siirestapi.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatementDto {

    private StatementRecordHourDto byHour;
    private StatementRecordLecturesDto byLecture;

}
