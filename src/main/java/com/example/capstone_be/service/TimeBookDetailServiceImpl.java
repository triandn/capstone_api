package com.example.capstone_be.service;


import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.daybook.TimeBookDetailDto;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.DayBook;
import com.example.capstone_be.model.ImageDetail;
import com.example.capstone_be.model.TimeBookDetail;
import com.example.capstone_be.repository.TimeBookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class TimeBookDetailServiceImpl implements TimeBookDetailService {
    private final ModelMapper mapper;

    private final TimeBookRepository timeBookRepository;

    public TimeBookDetailServiceImpl(ModelMapper mapper, TimeBookRepository timeBookRepository) {
        this.mapper = mapper;
        this.timeBookRepository = timeBookRepository;
    }

    @Override
    public List<TimeBookDetailDto> getAllTimeBookDetail() {
        List<TimeBookDetail> timeBookDetailList = timeBookRepository.findAll();
        List<TimeBookDetailDto> timeBookDetailDtoList = new ArrayList<>();

        for (TimeBookDetail timeBookDetail : timeBookDetailList) {
            timeBookDetailDtoList.add(mapper.map(timeBookDetail,TimeBookDetailDto.class));
        }
        return timeBookDetailDtoList;
    }

    @Override
    @Transactional
    public TimeBookDetailDto createTimeBookDetail(TimeBookDetailDto timeBookDetailDto) {
        timeBookRepository.save(mapper.map(timeBookDetailDto,TimeBookDetail.class));
        return timeBookDetailDto;
    }

    @Override
    public void deleteByTimeBookId(UUID id) {
        TimeBookDetail timeBookDetail = timeBookRepository.findById(id).orElseThrow(() -> new NotFoundException("TimeBooking not found"));
        timeBookRepository.deleteById(id);
    }

    @Override
    public TimeBookDetailDto updateByTimeBookId(TimeBookDetailDto timeBookDetailDto, UUID id) {
        final TimeBookDetail updatedTimeBook = timeBookRepository.findById(id)
                .map(timeBookDetail -> {
                    timeBookDetail.setTimeId(timeBookDetailDto.getTimeId());
                    timeBookDetail.setStart_time(timeBookDetailDto.getStart_time());
                    timeBookDetail.setEnd_time(timeBookDetailDto.getEnd_time());
                    return timeBookRepository.save(timeBookDetail);
                })
                .orElseGet(() -> {
                    timeBookDetailDto.setTimeId(id);
                    return timeBookRepository.save(mapper.map(timeBookDetailDto, TimeBookDetail.class));
                });

        return mapper.map(updatedTimeBook, TimeBookDetailDto.class);
    }

    @Override
    public TimeBookDetailDto getTimeBookingById(UUID id) {
        TimeBookDetail timeBookDetail = timeBookRepository.findById(id).orElseThrow(() -> new NotFoundException("TimeBook not found"));
        return mapper.map(timeBookDetail,TimeBookDetailDto.class);
    }

    @Override
    @Transactional
    public List<TimeBookDetailDto> createListTimeBookDetail(List<TimeBookDetailDto> timeBookDetailDtoList) {
        List<TimeBookDetail> timeBookDetails = new ArrayList<>();
        for (TimeBookDetailDto timeBookDetailDto: timeBookDetailDtoList) {
            timeBookDetails.add(mapper.map(timeBookDetailDto,TimeBookDetail.class));
        }
        timeBookRepository.saveAll(timeBookDetails);
        return timeBookDetailDtoList;
    }
}
