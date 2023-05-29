package com.example.capstone_be.dto.daybook;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBookViewDto {

    private UUID timeId ;

    private LocalTime start_time;

    private LocalTime end_time;

    private Boolean isDeleted;

}
