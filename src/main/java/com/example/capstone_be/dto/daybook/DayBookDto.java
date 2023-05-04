package com.example.capstone_be.dto.daybook;

import com.example.capstone_be.util.enums.DayBookStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayBookDto implements Serializable {

    private UUID dayBookId;

    private Date day_name;

    private Long tourId;

    private String status = DayBookStatusEnum.AVAILABLE.toString();

}

