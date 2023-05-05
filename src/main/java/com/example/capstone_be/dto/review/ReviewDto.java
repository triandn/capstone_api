package com.example.capstone_be.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto implements Serializable {

    private UUID reviewId = UUID.randomUUID();

    private String comment;

    private Integer rating;

    private UUID userId;

    private Long tourId;
}
