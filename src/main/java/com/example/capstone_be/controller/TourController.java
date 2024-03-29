package com.example.capstone_be.controller;


import com.example.capstone_be.dto.daybook.DayTimeCeateDto;
import com.example.capstone_be.dto.tour.*;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.model.User;
import com.example.capstone_be.repository.TourRepository;
import com.example.capstone_be.repository.UserRepository;
import com.example.capstone_be.response.TourRespone;
import com.example.capstone_be.response.TourResponseByCategoryName;
import com.example.capstone_be.response.TourResponseByOwner;
import com.example.capstone_be.response.UpdateResponse;
import com.example.capstone_be.service.TourService;
import com.example.capstone_be.util.common.CommonFunction;
import com.example.capstone_be.util.common.ErrorResponse;
import io.jsonwebtoken.Claims;
import jdk.dynalink.linker.LinkerServices;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.time.LocalDate;
import java.util.*;

import static com.example.capstone_be.util.ValidUtils.getMessageBindingResult;

@RestController
@RequestMapping("/tour")
public class TourController {
    private final TourService tourService;

    private final TourRepository tourRepository;

    private  final UserRepository userRepository;

    public TourController(TourService tourService, TourRepository tourRepository, UserRepository userRepository) {
        this.tourService = tourService;
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/create/")
    public ResponseEntity<?> createTour(@RequestBody TourCreateDto tourDto, HttpServletRequest request) {
        String bearerToken = CommonFunction.getBearToken(request);
        Claims claims = CommonFunction.getClaims(bearerToken);
        String email = claims.getSubject();
        User user = userRepository.getUserByUserEmail(email);
        System.out.println("User Id: " + user.getUserId());
        tourService.createTour(tourDto,user.getUserId());
        return new ResponseEntity<>(tourDto, HttpStatus.CREATED);
    }

    @PostMapping(path = "/create-tour/")
    public ResponseEntity<?> createTourNew(@RequestBody TourCreateDto tourDto, HttpServletRequest request) {
        String bearerToken = CommonFunction.getBearToken(request);
        Claims claims = CommonFunction.getClaims(bearerToken);
        String email = claims.getSubject();
        User user = userRepository.getUserByUserEmail(email);
        System.out.println("User Id: " + user.getUserId());
        tourService.createTour(tourDto,user.getUserId());
        return new ResponseEntity<>(tourDto, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public TourRespone getAllTour(@RequestParam(defaultValue = "1") Integer pageNo,
                                  @RequestParam(defaultValue = "5") Integer pageSize) {
        return tourService.getAll(pageNo,pageSize);
    }

    @GetMapping("/search-view-port")
    public TourRespone searchViewPort(@RequestParam(defaultValue = "1") Integer pageNo,
                                  @RequestParam(defaultValue = "5") Integer pageSize,@RequestBody ViewPortSearchDto viewPortSearchDto) {
        return tourService.getTourViewPort(viewPortSearchDto.getNorthEastLat(),
                                                    viewPortSearchDto.getSouthWestLat(),
                                                    viewPortSearchDto.getNorthEastLng(),
                                                    viewPortSearchDto.getSouthWestLng(),
                                                    pageNo,pageSize);
    }
    @GetMapping("/{categoryName}/{northEastLat}/{northEastLng}/{southWestLat}/{southWestLng}/")
    public TourResponseByCategoryName getTourByCategoryName(@PathVariable String categoryName,
                                                         @RequestParam(defaultValue = "1") Integer pageNo,
                                                         @RequestParam(defaultValue = "5") Integer pageSize,
                                                         @PathVariable String northEastLat,
                                                         @PathVariable String northEastLng,
                                                         @PathVariable String southWestLat,
                                                         @PathVariable String southWestLng) {
//        Double northLat = Double.valueOf(northEastLat);
//        Double northLng = Double.valueOf(northEastLng);
//        Double southLat = Double.valueOf(southWestLat);
//        Double southLng = Double.valueOf(southWestLng);
        return tourService.getTourByCategoryName(categoryName,pageNo,pageSize,northEastLat,northEastLng,southWestLat,southWestLng);
    }

    @GetMapping("/tour-detail/{tourId}")
    public ResponseEntity<TourDetailDto> getTourDetailById(@PathVariable Long tourId) {
        TourDetailDto tourDetailDto = tourService.getTourDetail(tourId);
        return new ResponseEntity<>(tourDetailDto, HttpStatus.OK);
    }

    @DeleteMapping("/tour-delete/{id}")
    public ResponseEntity<TourDto> deleteTour(@PathVariable Long id) {
        tourService.deleteByTourId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/tour-update/{id}")
    public Tour updateTour(@PathVariable Long id,@RequestBody Map<String,Object> fields) {
        return tourService.updateTourByField(id,fields);
    }
    @PatchMapping("/tour-update-time/{id}")
    public ResponseEntity<?> updateStartTimeAndEndTimeTour(@PathVariable Long id, @RequestBody UpdateTimeTourDto updateTimeTourDto) {
        tourService.updateTimeTour(updateTimeTourDto,id);
        return new ResponseEntity<>("Update Success",HttpStatus.OK);
    }
    @GetMapping("/tour-owner/")
    public ResponseEntity<?> getTourByOwner(HttpServletRequest request,
                                            @RequestParam(defaultValue = "1") Integer pageNo,
                                            @RequestParam(defaultValue = "5") Integer pageSize) {
        String bearerToken = CommonFunction.getBearToken(request);
        Claims claims = CommonFunction.getClaims(bearerToken);
        String email = claims.getSubject();
        User user = userRepository.getUserByUserEmail(email);
        TourResponseByOwner tourResponseByOwner = tourService.getTourByUserId(user.getUserId(),pageNo,pageSize);
        return new ResponseEntity<>(tourResponseByOwner, HttpStatus.OK);
    }

    @PostMapping(path = "/create-day-time/{tour_id}")
    public ResponseEntity<?> createTourNew(@RequestBody DayTimeCeateDto dayTimeCeateDto, HttpServletRequest request,
                                           @PathVariable Long tour_id) {
        DateTime startDay = DateTime.parse(dayTimeCeateDto.getStartDay());
        DateTime endDay = DateTime.parse(dayTimeCeateDto.getEndDay());
        tourService.createDate(startDay,endDay,tour_id);
        return  new ResponseEntity<>("Update Success",HttpStatus.OK);
    }
}
