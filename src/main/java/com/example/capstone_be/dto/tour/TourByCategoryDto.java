package com.example.capstone_be.dto.tour;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourByCategoryDto implements Serializable {

    private Long tourId;

    private String title;

    private Float rating;

    private String city;

    private Float priceOnePerson;

    private String imageMain;

    private String working;

    private String destination;

    private String destinationDescription;
}
