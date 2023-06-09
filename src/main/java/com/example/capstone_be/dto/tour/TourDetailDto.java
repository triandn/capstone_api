package com.example.capstone_be.dto.tour;

import com.example.capstone_be.dto.daybook.TimeBookEnd;
import com.example.capstone_be.dto.daybook.TimeBookStart;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.image.ImageViewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourDetailDto implements Serializable {

    private Long tourId;

    private String title;

    private Float rating;

    private String city;

    private Double priceOnePerson;

    private String imageMain;

    private String working;

    private String latitude;

    private String longitude;

    private String destination;

    private String destinationDescription;

    private int timeSlotLength;

    private Double avgRating;

    private Boolean isDeleted;

    private List<ImageViewDto> images;

    private UUID userId;

    private TimeBookStart timeBookStart;

    private TimeBookEnd timeBookEnd;

//    private List<>
}
