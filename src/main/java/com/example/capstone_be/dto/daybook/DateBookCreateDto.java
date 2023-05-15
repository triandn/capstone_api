package com.example.capstone_be.dto.daybook;

import com.example.capstone_be.util.enums.DayBookStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateBookCreateDto implements Serializable {

    private UUID dayBookId = UUID.randomUUID();

    private Date date_name;

    private  String status = DayBookStatusEnum.AVAILABLE.toString();

    private Long tourId;
}