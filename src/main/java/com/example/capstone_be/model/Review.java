package com.example.capstone_be.model;

import com.example.capstone_be.util.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="reviews")
public class Review extends BaseEntity {
    @Id
    @Column(name = "review_id", nullable = false)
    private UUID reviewId = UUID.randomUUID();

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "rating", nullable = false, columnDefinition = "INTEGER CHECK (rating > 0 AND rating <= 5)")
    private Integer rating;

    @Column(name = "user_id", nullable = false,insertable = false,updatable = false)
    private UUID userId;

    @Column(name = "tour_id", nullable = false,insertable = false,updatable = false)
    private Long tourId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
