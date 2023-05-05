package com.example.capstone_be.dto.daybook;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBookViewDto {

    private UUID timeId ;

    private Time start_time;

    private Time end_time;
}
