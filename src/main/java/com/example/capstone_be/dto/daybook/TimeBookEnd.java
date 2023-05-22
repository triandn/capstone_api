package com.example.capstone_be.dto.daybook;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBookEnd implements Serializable {
    private int hour;

    private int minutes;
}
