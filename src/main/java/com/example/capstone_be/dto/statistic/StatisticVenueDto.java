package com.example.capstone_be.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticVenueDto implements Serializable {
    private String label;
    private Double value;
}
