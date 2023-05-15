package com.example.capstone_be.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="guests")
public class Guest extends  BaseEntity{
    @Id
    @Column(name = "guest_id", nullable = false)
    private UUID guestId = UUID.randomUUID();

    @Column(name = "guest_type")
    private String guestType;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "tour_id", nullable = false,insertable = false,updatable = false)
    private Long tourId;

    @Column(name = "time_id", nullable = false,insertable = false,updatable = false)
    private UUID timeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_id")
    private TimeBookDetail timeBookDetail;
}
