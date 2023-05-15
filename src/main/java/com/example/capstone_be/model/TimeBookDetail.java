package com.example.capstone_be.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="time_book_details")
public class TimeBookDetail extends BaseEntity{
    @Id
    @Column(name = "time_id", nullable = false)
    private UUID timeId = UUID.randomUUID();

    @Column(name = "start_time", nullable = false,columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Timestamp start_time;

    @Column(name = "end_time", nullable = false,columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Timestamp end_time;

    @Column(name = "tour_id", nullable = false,insertable = false,updatable = false)
    private Long tourId;

    @Column(name = "day_book_id", nullable = false,insertable = false,updatable = false)
    private UUID day_book_id;

    @Column(name = "user_id", nullable = false,insertable = false,updatable = false)
    private UUID user_id;

    @Column(name = "is_payment")
    private Boolean isPayment = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_book_id", nullable = false)
    private DayBook dayBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}