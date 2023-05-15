package com.example.capstone_be.dto.daybook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBookDetailDto implements Serializable {

    private UUID timeId ;

    private Timestamp start_time;

    private Timestamp end_time;

    private Long tourId;

    private UUID day_book_id;

    private UUID user_id;
}
