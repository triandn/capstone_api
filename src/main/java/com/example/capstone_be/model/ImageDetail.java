package com.example.capstone_be.model;


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
@Entity(name="image_details")
public class ImageDetail extends BaseEntity{
    @Id
    @Column(name = "image_id", nullable = false)
    private UUID imageId = UUID.randomUUID();

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "tour_id", nullable = false,insertable = false,updatable = false)
    private Long tourId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;
}
