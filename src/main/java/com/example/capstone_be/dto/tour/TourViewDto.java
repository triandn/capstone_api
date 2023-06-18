package com.example.capstone_be.dto.tour;

import com.example.capstone_be.dto.image.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourViewDto implements Serializable {

    private Long tourId;

    private String title;

    private Float rating;

    private String city;

    private Double priceOnePerson;

    private String imageMain;

    private String working;

    private Float latitude;

    private Float longitude;

    private String destination;

    private String destinationDescription;

    private int timeSlotLength;

    private Long categoryId;

    private String categoryName;

    private Double avgRating;

    private Boolean isDeleted;

    private UUID userId;

}
