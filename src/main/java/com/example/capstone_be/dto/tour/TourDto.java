package com.example.capstone_be.dto.tour;

import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.model.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourDto implements Serializable {

    private Long tourId;

    private String title;

    private String city;

    private Float priceOnePerson;

    private String imageMain;

    private String working;

    private String latitude;

    private String longitude;

    private String destinationDescription;

    private int timeSlotLength;

    private Boolean isDeleted;

    private UUID userId;
}
