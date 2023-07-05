package com.example.capstone_be.service;

import com.example.capstone_be.dto.category.CategoryDto;
import com.example.capstone_be.dto.daybook.TimeBookEnd;
import com.example.capstone_be.dto.daybook.TimeBookStart;
import com.example.capstone_be.dto.tour.*;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.response.TourRespone;
import com.example.capstone_be.response.TourResponseByCategoryName;
import com.example.capstone_be.response.TourResponseByOwner;
import org.joda.time.DateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public interface TourService {

    TourCreateDto createTour(TourCreateDto tourDto, UUID userId);

    TourRespone getAll(Integer pageNo, Integer pageSize);

    TourResponseByCategoryName getTourByCategoryName(String categoryName,
                                                     Integer pageNo,
                                                     Integer pageSize,
                                                     String northEastLat,
                                                     String northEastLng,
                                                     String southWestLat,
                                                     String southWestLng);

    TourDetailDto getTourDetail(Long tourId);

    ResponseEntity<?> deleteByTourId(Long id);

    Tour updateTourByField(Long id, Map<String, Object> fields);

    TourResponseByOwner getTourByUserId(UUID userId, Integer pageNo, Integer pageSize);

    void updateTimeTour(UpdateTimeTourDto updateTimeTourDto, Long tourId);

    TourRespone getTourViewPort(String northEastLat,String southWestLat, String northEastLng,
                                String southWestLng,Integer pageNo, Integer pageSize);

    void createDate(DateTime startDay, DateTime endDay,Long tourId);

}
