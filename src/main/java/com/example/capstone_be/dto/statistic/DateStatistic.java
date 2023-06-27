package com.example.capstone_be.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateStatistic implements Serializable {
    private String start;
    private String end;
}
