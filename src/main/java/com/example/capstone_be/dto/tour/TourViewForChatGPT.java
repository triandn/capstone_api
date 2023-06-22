package com.example.capstone_be.dto.tour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourViewForChatGPT implements Serializable {
    private Long tourId;

    private String title;

    private String city;

    private Double priceOnePerson;

    private String imageMain;

    private String working;

    private String destinationDescription;

    private String categoryName;

}
