package com.example.capstone_be.controller;


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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createTour(@Valid @RequestBody TourCreateDto tourDto, HttpServletRequest request) {
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
        final TourRespone tourViewDtos = tourService.getAll(pageNo,pageSize);
        return tourViewDtos;
    }

    @GetMapping("/search-view-port")
    public TourRespone searchViewPort(@RequestParam(defaultValue = "1") Integer pageNo,
                                  @RequestParam(defaultValue = "5") Integer pageSize,@RequestBody ViewPortSearchDto viewPortSearchDto) {
        final TourRespone tourViewDtos = tourService.getTourViewPort(viewPortSearchDto.getNorthEastLat(),
                                                    viewPortSearchDto.getSouthWestLat(),
                                                    viewPortSearchDto.getNorthEastLng(),
                                                    viewPortSearchDto.getSouthWestLng(),
                                                    pageNo,pageSize);
        return tourViewDtos;
    }

    @GetMapping("/{categoryName}")
    public TourResponseByCategoryName getTourByCategoryName(@PathVariable String categoryName,
                                                                         @RequestParam(defaultValue = "1") Integer pageNo,
                                                                         @RequestParam(defaultValue = "5") Integer pageSize) {
        TourResponseByCategoryName tourResponseByCategoryName = tourService.getTourByCategoryName(categoryName,pageNo,pageSize);
        return tourResponseByCategoryName;
    }

    @GetMapping("/tour-detail/{tourId}")
    public ResponseEntity<TourDetailDto> getTourDetailById(@PathVariable Long tourId) {
        TourDetailDto tourDetailDto = tourService.getTourDetail(tourId);
        return new ResponseEntity(tourDetailDto, HttpStatus.OK);
    }

    @DeleteMapping("/tour-delete/{id}")
    public ResponseEntity<TourDto> deleteTour(@PathVariable Long id) {
        tourService.deleteByTourId(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
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
        return new ResponseEntity(tourResponseByOwner, HttpStatus.OK);
    }
    @GetMapping("/tour-chat-gpt/")
    public ResponseEntity<?> getTourByOwner() {
        List<TourViewForChatGPT> tourViewForChatGPTList = tourService.getAllForChatGPT();
        return new ResponseEntity(tourViewForChatGPTList, HttpStatus.OK);
    }
}
