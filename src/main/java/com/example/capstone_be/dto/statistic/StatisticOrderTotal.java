package com.example.capstone_be.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticOrderTotal implements Serializable {
    private String label;
    private int value;
}
