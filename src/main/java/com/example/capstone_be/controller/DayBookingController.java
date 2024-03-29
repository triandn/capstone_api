package com.example.capstone_be.controller;


import com.example.capstone_be.dto.daybook.DateBookCreateDto;
import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.daybook.DayBookViewDto;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.image.ImageViewDto;
import com.example.capstone_be.response.DayBookPagingResponse;
import com.example.capstone_be.response.DayPagingResponse;
import com.example.capstone_be.response.UpdateResponse;
import com.example.capstone_be.service.DayBookService;
import lombok.Data;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.capstone_be.util.ValidUtils.getMessageBindingResult;

@RestController
@RequestMapping("/day-booking")
public class DayBookingController {
    private final DayBookService dayBookService;

    public DayBookingController(DayBookService dayBookService) {
        this.dayBookService = dayBookService;
    }

    @PostMapping("/create/")
    public ResponseEntity<DateBookCreateDto> createDayBooking(@RequestBody DateBookCreateDto dayBookDto) {
        dayBookService.createDayBooking(dayBookDto);
        return new ResponseEntity<>(dayBookDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDayBooking(@PathVariable UUID id) {
        return dayBookService.deleteByDayBookId(id);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update-day-time/")
    public ResponseEntity<?> updateDayBooking(@RequestBody List<Map<String,Object>> fields) {
        dayBookService.updateDayByField(fields);
        UpdateResponse updateResponse = new UpdateResponse();
        updateResponse.setMessage("UPDATED SUCCESS");
        updateResponse.setStatus_code(HttpStatus.OK.toString());
        return new ResponseEntity(updateResponse, HttpStatus.OK);
    }

    @GetMapping("/all/")
    public ResponseEntity<List<DayBookDto>> getAllDayBooking() {
        final List<DayBookDto> dayBookDtoList = dayBookService.getAllDayBook();
        return new ResponseEntity<>(dayBookDtoList,HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDayBookingDetail(@PathVariable UUID id) {
        final DayBookViewDto dayBookDto = dayBookService.getDayBookingById(id);
        return new ResponseEntity<>(dayBookDto,HttpStatus.OK);
    }
    @GetMapping("/day-time/{tour_id}/{start_time}/{end_time}")
    public ResponseEntity<?> getDayTimePagingByTourId(@PathVariable Long tour_id,
                                                 @PathVariable String start_time,
                                                 @PathVariable String end_time,
                                                 @RequestParam(defaultValue = "1") Integer pageNo,
                                                 @RequestParam(defaultValue = "5") Integer pageSize) throws ParseException {
        DayBookPagingResponse dayBookViewDtoList = dayBookService.getDayAndTimeByTourId(tour_id,start_time,end_time,pageNo,pageSize);
        return new ResponseEntity<>(dayBookViewDtoList,HttpStatus.OK);
    }
    @GetMapping("/day-paging/all/{tour_id}/{start_time}/{end_time}")
    public ResponseEntity<?> getDayBookingPagingByTourId(@PathVariable Long tour_id,
                                                 @PathVariable String start_time,
                                                 @PathVariable String end_time,
                                                 @RequestParam(defaultValue = "1") Integer pageNo,
                                                 @RequestParam(defaultValue = "5") Integer pageSize) throws ParseException {
        DayPagingResponse dayBookViewDtoList = dayBookService.getDayBookByTourIdPaging(tour_id,start_time,end_time,pageNo,pageSize);
        return new ResponseEntity<>(dayBookViewDtoList,HttpStatus.OK);
    }
    @GetMapping("/day/all/{tour_id}")
    public ResponseEntity<?> getDayBookingNotPagingByTourId(@PathVariable Long tour_id) {
        List<DayBookDto> dayBookViewDtoList = dayBookService.getDayBookByTourId(tour_id);
        return new ResponseEntity<>(dayBookViewDtoList,HttpStatus.OK);
    }
}
