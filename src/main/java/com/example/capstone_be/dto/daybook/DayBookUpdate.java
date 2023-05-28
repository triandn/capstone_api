package com.example.capstone_be.dto.daybook;

import com.example.capstone_be.model.TimeBookDetail;
import com.example.capstone_be.util.enums.DayBookStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayBookUpdate {

    private UUID dayBookId;

    private Date date_name;

    private Long tourId;

    private String status = DayBookStatusEnum.AVAILABLE.toString();

    private Boolean isDeleted;

    List<TimeBookDetail> timeBookDetailList;
}
