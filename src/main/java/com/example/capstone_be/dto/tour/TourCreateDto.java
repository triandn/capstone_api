package com.example.capstone_be.dto.tour;

import com.example.capstone_be.dto.daybook.TimeBookEnd;
import com.example.capstone_be.dto.daybook.TimeBookStart;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.model.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourCreateDto implements Serializable {
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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp checkIn;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp checkOut;

    private String destinationDescription;

    private int timeSlotLength;

    private Set<Category> categories;

    private List<ImageDto> imageDtoList;

    private DateTime startDay;

    private DateTime endDay;

    private TimeBookStart timeBookStart;

    private TimeBookEnd timeBookEnd;

    private UUID userId;
}
