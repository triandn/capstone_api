package com.example.capstone_be.dto.tour;

import com.example.capstone_be.dto.daybook.TimeBookEnd;
import com.example.capstone_be.dto.daybook.TimeBookStart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTimeTourDto {
    private TimeBookStart timeBookStart;
    private TimeBookEnd timeBookEnd;
    private int timeSlotLength;
}
