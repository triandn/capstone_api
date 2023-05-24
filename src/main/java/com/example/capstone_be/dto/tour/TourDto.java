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

    @NotBlank(message = "Vui lòng không để trống tiêu đề!")
    private String title;

    private Float rating;

    @NotBlank(message = "Vui lòng không để trống tên thành phố!")
    private String city;

    @NotNull(message = "Vui lòng không để trống giá!")
    private Float priceOnePerson;

    @NotBlank(message = "vui lòng không để trống url ảnh!")
    private String imageMain;

    @NotBlank(message = "Vui lòng không để trống công việc sẽ làm!")
    private String working;

    @NotBlank(message = "Vui lòng không để trống vĩ độ!")
    private String latitude;

    @NotBlank(message = "Vui lòng không để trống kinh độ!")
    private String longitude;

    private String destination;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp checkIn;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp checkOut;

    @NotBlank(message = "Vui lòng không để trống mô tả tour!")
    private String destinationDescription;

    private int timeSlotLength;

    private Boolean isDeleted;

    private Set<Category> categories;

    private UUID userId;
}
