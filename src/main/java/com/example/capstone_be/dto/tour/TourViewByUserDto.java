package com.example.capstone_be.dto.tour;

import com.example.capstone_be.dto.daybook.DayBookViewDto;
import com.example.capstone_be.dto.daybook.TimeBookEnd;
import com.example.capstone_be.dto.daybook.TimeBookStart;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.image.ImageViewDto;
import com.example.capstone_be.dto.order.OrderDto;
import com.example.capstone_be.model.Category;
import com.example.capstone_be.model.DayBook;
import com.example.capstone_be.model.TimeBookDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourViewByUserDto {
    private Long tourId;
    private String title;
    private String city;
    private Double priceOnePerson;
    private String imageMain;
    private String working;
    private Double latitude;
    private Double longitude;
    private String destination;
    private String destinationDescription;
    private int timeSlotLength;
    private Long categoryId;
    private String categoryName;
    private Boolean isDeleted;
    List<OrderDto> orderDtoList;
}
