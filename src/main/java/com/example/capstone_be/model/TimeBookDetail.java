package com.example.capstone_be.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="time_book_details")
public class TimeBookDetail extends BaseEntity{
    @Id
    @Column(name = "time_id", nullable = false,updatable = false)
    private UUID timeId = UUID.randomUUID();

    @Column(name = "start_time", nullable = false)
    private LocalTime start_time;

    @Column(name = "end_time", nullable = false)
    private LocalTime end_time;

    @Column(name = "day_book_id", nullable = false,insertable = false,updatable = false)
    private UUID day_book_id;

    @Column(name = "is_payment")
    private Boolean isPayment = false;

    @Column(name="is_deleted")
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "day_book_id", nullable = false)
    private DayBook dayBook;
}