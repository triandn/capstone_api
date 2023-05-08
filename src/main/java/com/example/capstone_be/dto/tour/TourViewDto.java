package com.example.capstone_be.dto.tour;

import com.example.capstone_be.dto.image.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourViewDto implements Serializable {

    private Long tourId;

    private String title;

    private Float rating;

    private String city;

    private Float priceOnePerson;

    private String imageMain;

    private String working;

    private String latitude;

    private String longitude;

    private String destination;

    private String destinationDescription;

    private Long categoryId;

    private String categoryName;

    private Double avgRating;

}
