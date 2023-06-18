package com.example.capstone_be.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDto implements Serializable {
    private int totalOrder;
    private Long tourId;
    private String tourTitle;
}
