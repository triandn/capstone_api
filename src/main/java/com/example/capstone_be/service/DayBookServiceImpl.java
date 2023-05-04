package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.DayBook;
import com.example.capstone_be.model.ImageDetail;
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

    public DayBookServiceImpl(ModelMapper mapper, DayBookRepository dayBookRepository) {
        this.mapper = mapper;
        this.dayBookRepository = dayBookRepository;
    }

    @Override
    public List<DayBookDto> getAllDayBook() {
        List<DayBook> dayBookList = dayBookRepository.findAll();
        List<DayBookDto> dayBookDtoList = new ArrayList<>();

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
        return mapper.map(dayBook,DayBookDto.class);
    }
}
