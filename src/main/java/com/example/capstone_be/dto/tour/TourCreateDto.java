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

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "Vui lòng nhập tiêu đề!")
    private String title;

    private Float rating;

    @NotBlank(message = "Vui lòng nhập tên thành phố!")
    private String city;

    @NotNull(message = "Vui lòng nhập giá!")
    private Float priceOnePerson;

    @NotBlank(message = "Vui lòng nhập url ảnh!")
    @NotNull(message = "Vui lòng nhập url ảnh!")
    private String imageMain;

    @NotBlank(message = "Vui lòng nhập công việc sẽ thực hiện!")
    private String working;

    @NotBlank(message = "Vui lòng nhập vĩ độ!")
    private Float latitude;

    @NotBlank(message = "Vui lòng nhập kinh độ!")
    private Float longitude;

    private String destination;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp checkIn;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp checkOut;

    @NotBlank(message = "Vui lòng nhập mô tả chi tiết!")
    private String destinationDescription;

    @NotNull(message = "Vui lòng nhập khoảng thời gian!")
    private int timeSlotLength;

    @NotNull(message = "Vui lòng chọn danh mục!")
    private Set<Category> categories;

    @NotNull(message = "Vui lòng nhập danh sách ảnh!")
    private List<ImageDto> imageDtoList;

    @NotNull(message = "Vui lòng nhập ngày bắt đầu hành trình!")
    private DateTime startDay;

    @NotNull(message = "Vui lòng nhập ngày kết thúc hành trình!")
    private DateTime endDay;

    @NotNull(message = "Vui lòng nhập thời khung thời gian bắt đầu!")
    private TimeBookStart timeBookStart;

    @NotNull(message = "Vui lòng nhập khung thời gian kết thúc!")
    private TimeBookEnd timeBookEnd;

    private UUID userId;
}
