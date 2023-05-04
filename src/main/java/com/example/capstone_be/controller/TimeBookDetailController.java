package com.example.capstone_be.controller;


import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.daybook.TimeBookDetailDto;
import com.example.capstone_be.service.TimeBookDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

import static com.example.capstone_be.util.ValidUtils.getMessageBindingResult;

@RestController
@RequestMapping("/time-book")
public class TimeBookDetailController {
    private final TimeBookDetailService timeBookDetailService;

    public TimeBookDetailController(TimeBookDetailService timeBookDetailService) {
        this.timeBookDetailService = timeBookDetailService;
    }
    @PostMapping("/create")
    public ResponseEntity<TimeBookDetailDto> createTimeBookForDay(@RequestBody TimeBookDetailDto timeBookDetailDto) {
        timeBookDetailService.createTimeBookDetail(timeBookDetailDto);
        return new ResponseEntity<>(timeBookDetailDto, HttpStatus.CREATED);
    }

    @PostMapping("/create-list")
    public ResponseEntity<List<TimeBookDetailDto>> createListTimeBookForDay(@RequestBody List<TimeBookDetailDto> timeBookDetailDtos) {
        timeBookDetailService.createListTimeBookDetail(timeBookDetailDtos);
        return new ResponseEntity<>(timeBookDetailDtos, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TimeBookDetailDto> deleteTimeBook(@PathVariable UUID id) {
        timeBookDetailService.deleteByTimeBookId(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateTimeBookForDay(@RequestBody @Valid TimeBookDetailDto timeBookDetailDto, @PathVariable UUID id, final BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String msg = getMessageBindingResult(bindingResult);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        TimeBookDetailDto updatedTimeBookDto = timeBookDetailService.updateByTimeBookId(timeBookDetailDto, id);
        return new ResponseEntity(updatedTimeBookDto, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<TimeBookDetailDto>> getAllTimeBookingForDay() {
        final List<TimeBookDetailDto> timeBookDtoList = timeBookDetailService.getAllTimeBookDetail();
        return new ResponseEntity<>(timeBookDtoList,HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<TimeBookDetailDto> getTimeBookingForDayDetail(@PathVariable UUID id) {
        final TimeBookDetailDto timeBookDetailDto = timeBookDetailService.getTimeBookingById(id);
        return new ResponseEntity<>(timeBookDetailDto,HttpStatus.OK);
    }
}
