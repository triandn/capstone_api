package com.example.capstone_be.dto.daybook;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBookDetailDto implements Serializable {

    private UUID timeId = UUID.randomUUID();

    private LocalTime start_time;

    private LocalTime end_time;

    private Boolean isPayment = false;

    private Boolean isDeleted;

    private UUID day_book_id;

}
