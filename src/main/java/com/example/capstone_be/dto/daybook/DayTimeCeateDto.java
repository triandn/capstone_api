package com.example.capstone_be.dto.daybook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayTimeCeateDto implements Serializable {
    String startDay;
    String endDay;
}
