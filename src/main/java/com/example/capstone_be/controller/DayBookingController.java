package com.example.capstone_be.controller;


import com.example.capstone_be.dto.daybook.DateBookCreateDto;
import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.daybook.DayBookViewDto;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.image.ImageViewDto;
import com.example.capstone_be.service.DayBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
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

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateDayBooking(@RequestBody @Valid DayBookDto dayBookDto, @PathVariable UUID id, final BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String msg = getMessageBindingResult(bindingResult);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        DayBookDto updatedDayBookDto = dayBookService.updateByDayBookId(dayBookDto, id);
        return new ResponseEntity(updatedDayBookDto, HttpStatus.OK);
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
}
