package com.example.capstone_be.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="daybooks")
public class DayBook extends BaseEntity{
    @Id
    @Column(name = "day_book_id", nullable = false, updatable = false)
    private UUID dayBookId = UUID.randomUUID();

    @Column(name = "date_name", nullable = false)
    private Date date_name;

    @Column(name = "tour_id", nullable = false,insertable = false,updatable = false)
    private Long tourId;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name="is_deleted")
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

}
