package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.daybook.TimeBookDetailDto;
import com.example.capstone_be.dto.daybook.TimeBookViewDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.DayBook;
import com.example.capstone_be.model.TimeBookDetail;
import com.example.capstone_be.repository.DayBookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DayBookServiceImpl implements DayBookService {

    private final ModelMapper mapper;
    private final DayBookRepository dayBookRepository;
    private final TimeBookDetailService timeBookDetailService;

    public DayBookServiceImpl(ModelMapper mapper, DayBookRepository dayBookRepository, TimeBookDetailService timeBookDetailService) {
        this.mapper = mapper;
        this.dayBookRepository = dayBookRepository;
        this.timeBookDetailService = timeBookDetailService;
    }

    @Override
    public List<DayBookDto> getAllDayBook() {
        List<DayBook> dayBookList = dayBookRepository.findAll();
        List<DayBookDto> dayBookDtoList = new ArrayList<>();
//        List<TimeBookViewDto> timeBookDtoList = new ArrayList<>();
//        DayBookDto dayBookDto = null;
//        for (DayBook daybook: dayBookList) {
//            dayBookDto = new DayBookDto();
//            timeBookDtoList = timeBookDetailService.getAllTimeBookForDayByDayBookId(daybook.getDayBookId());
//            dayBookDto.setDayBookId(daybook.getDayBookId());
//            dayBookDto.setDay_name(daybook.getDay_name());
//            dayBookDto.setTourId(daybook.getTourId());
//            dayBookDto.setStatus(daybook.getStatus());
//            dayBookDto.setTimeBookViewDtoList(timeBookDtoList);
//            dayBookDtoList.add(dayBookDto);
//        }
        for (DayBook daybook: dayBookList) {
            dayBookDtoList.add(mapper.map(daybook,DayBookDto.class));
        }
        return dayBookDtoList;
    }

    @Override
    @Transactional
    public DayBookDto createDayBooking(DayBookDto dayBookDto) {
        dayBookRepository.save(mapper.map(dayBookDto,DayBook.class));
        return dayBookDto;
    }

    @Override
    public void deleteByDayBookId(UUID id) {
        DayBook dayBook = dayBookRepository.findById(id).orElseThrow(() -> new NotFoundException("DayBooking not found"));
        dayBookRepository.deleteById(id);
    }

    @Override
    public DayBookDto updateByDayBookId(DayBookDto dayBookDto, UUID id) {
        final DayBook updatedDayBook = dayBookRepository.findById(id)
                .map(dayBook -> {
                    dayBook.setDay_name(dayBookDto.getDay_name());
                    dayBook.setDayBookId(dayBookDto.getDayBookId());
                    dayBook.setStatus(dayBookDto.getStatus());

                    return dayBookRepository.save(dayBook);
                })
                .orElseGet(() -> {
                    dayBookDto.setDayBookId(id);
                    return dayBookRepository.save(mapper.map(dayBookDto, DayBook.class));
                });

        return mapper.map(updatedDayBook, DayBookDto.class);
    }

    @Override
    public DayBookDto getDayBookingById(UUID id) {
        DayBook dayBook = dayBookRepository.findById(id).orElseThrow(() -> new NotFoundException("DayBooking not found"));
        DayBookDto dayBookDto = new DayBookDto();
        List<TimeBookViewDto> timeBookViewDtoList = timeBookDetailService.getAllTimeBookForDayByDayBookId(id);
        dayBookDto.setTourId(dayBook.getTourId());
        dayBookDto.setDayBookId(dayBook.getDayBookId());
        dayBookDto.setDay_name(dayBook.getDay_name());
        dayBookDto.setStatus(dayBook.getStatus());
        dayBookDto.setTimeBookViewDtoList(timeBookViewDtoList);
        return dayBookDto;
    }
}
