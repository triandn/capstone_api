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
public class DayBookViewDto implements Serializable {

    private UUID dayBookId;

    private String date_name;

    private Long tourId;

    private String status = DayBookStatusEnum.AVAILABLE.toString();

    private Boolean is_deleted;

    List<TimeBookViewDto> timeBookDetailList;

}
