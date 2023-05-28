package com.example.capstone_be.dto.daybook.updateDTO;

import com.example.capstone_be.dto.daybook.TimeBookViewDto;
import com.example.capstone_be.util.enums.DayBookStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayTimeUpdateDto {

    private UUID dayBookId;

    private Date date_name;

    private Long tourId;

    private String status = DayBookStatusEnum.AVAILABLE.toString();

    private Boolean is_deleted;

    List<TimeBookViewDto> timeBookViewDtoList;
}
