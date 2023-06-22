package com.example.capstone_be.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Call implements Serializable {
    private String model;
    private String prompt;
    private Integer max_tokens;
    private Double temperature;
}
