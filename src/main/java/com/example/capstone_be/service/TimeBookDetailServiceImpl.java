package com.example.capstone_be.service;


import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.daybook.DayBookUpdate;
import com.example.capstone_be.dto.daybook.TimeBookDetailDto;
import com.example.capstone_be.dto.daybook.TimeBookViewDto;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.DayBook;
import com.example.capstone_be.model.ImageDetail;
import com.example.capstone_be.model.TimeBookDetail;
import com.example.capstone_be.repository.TimeBookRepository;
import com.example.capstone_be.util.common.DeleteResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public List<TimeBookViewDto> getAllTimeBookDetail() {
        List<TimeBookDetail> timeBookDetailList = timeBookRepository.findAll();
        List<TimeBookViewDto> timeBookViewDtoList = new ArrayList<>();

        for (TimeBookDetail timeBookDetail : timeBookDetailList) {
            timeBookViewDtoList.add(mapper.map(timeBookDetail,TimeBookViewDto.class));
        }
        return timeBookViewDtoList;
    }

    @Override
    @Transactional
    public TimeBookDetailDto createTimeBookDetail(TimeBookDetailDto timeBookDetailDto) {
        timeBookRepository.saveTimeBookDetail(timeBookDetailDto.getStart_time(),timeBookDetailDto.getEnd_time(),
                timeBookDetailDto.getDay_book_id(),timeBookDetailDto.getIsPayment()
        );
        return timeBookDetailDto;
    }

    @Override
    public ResponseEntity<?> deleteByTimeBookId(UUID id) {
        TimeBookDetail timeBookDetail = timeBookRepository.findById(id).orElseThrow(() -> new NotFoundException("TimeBooking not found"));
//        timeBookRepository.deleteById(id);
        if(timeBookDetail.getIsDeleted().equals(true)){
            return new ResponseEntity<>(new DeleteResponse("THIS TIME BOOK IS DELETED"), HttpStatus.NOT_FOUND);

        }
        timeBookDetail.setIsDeleted(true);
        timeBookRepository.save(timeBookDetail);
        return new ResponseEntity<>(new DeleteResponse("DELETE SUCCESS"),HttpStatus.OK);
    }


    @Override
    public TimeBookDetailDto updateByTimeBookId(TimeBookDetailDto timeBookDetailDto, UUID id) {
        final TimeBookDetail updatedTimeBook = timeBookRepository.findById(id)
                .map(timeBookDetail -> {
                    timeBookDetail.setStart_time(timeBookDetailDto.getStart_time());
                    timeBookDetail.setEnd_time(timeBookDetailDto.getEnd_time());
                    timeBookDetail.setIsPayment(timeBookDetailDto.getIsPayment());
                    return timeBookRepository.save(timeBookDetail);
                })
                .orElseGet(() -> {
                    timeBookDetailDto.setTimeId(id);
                    return timeBookRepository.save(mapper.map(timeBookDetailDto, TimeBookDetail.class));
                });

        return mapper.map(updatedTimeBook, TimeBookDetailDto.class);
    }

    @Override
    public TimeBookViewDto getTimeBookingById(UUID id) {
        TimeBookDetail timeBookDetail = timeBookRepository.findById(id).orElseThrow(() -> new NotFoundException("TimeBook not found"));
        return mapper.map(timeBookDetail,TimeBookViewDto.class);
    }

    @Override
    @Transactional
    public List<TimeBookDetailDto> createListTimeBookDetail(List<TimeBookDetailDto> timeBookDetailDtoList) {
        List<TimeBookDetail> timeBookDetails = new ArrayList<>();
        TimeBookDetailDto timeBookDetailForSave = null;
        for (TimeBookDetailDto timeBookDetailDto: timeBookDetailDtoList) {
//            timeBookDetails.add(mapper.map(timeBookDetailDto,TimeBookDetail.class));
            timeBookDetailForSave = new TimeBookDetailDto();
            timeBookDetailForSave.setTimeId(timeBookDetailDto.getTimeId());
            timeBookDetailForSave.setDay_book_id(timeBookDetailDto.getDay_book_id());
            timeBookDetailForSave.setStart_time(timeBookDetailDto.getStart_time());
            timeBookDetailForSave.setEnd_time(timeBookDetailDto.getEnd_time());
            timeBookDetails.add(mapper.map(timeBookDetailForSave,TimeBookDetail.class));
        }
        timeBookRepository.saveAll(timeBookDetails);
        return timeBookDetailDtoList;
    }

    @Override
    public List<TimeBookViewDto> getAllTimeBookForDayByDayBookId(UUID dayBookId) {
        List<TimeBookDetail> timeBookDetailList = timeBookRepository.getAllListTimeBookByDayBookId(dayBookId);
        List<TimeBookViewDto> timeBookViewDtoList = new ArrayList<>();
        TimeBookViewDto timeBookViewDto = null;
        for (TimeBookDetail timeBookDetail: timeBookDetailList) {
            timeBookViewDto = new TimeBookViewDto();
//            timeBookViewDtoList.add(mapper.map(timeBookDetail,TimeBookViewDto.class));
            timeBookViewDto.setTimeId(timeBookDetail.getTimeId());
            timeBookViewDto.setStart_time(timeBookDetail.getStart_time());
            timeBookViewDto.setEnd_time(timeBookDetail.getEnd_time());
            timeBookViewDto.setIsDeleted(timeBookDetail.getIsDeleted());
            timeBookViewDtoList.add(timeBookViewDto);
        }
        return timeBookViewDtoList;
    }

    @Override
    public TimeBookDetail findTimeBookById(UUID id) {
        return timeBookRepository.findById(id).orElseThrow(() -> new NotFoundException("TimeBook not found"));
    }

    @Override
    public void updateTimeBookByField(List<Map<String,Object>> fields) {
        for (Map<String,Object> item : fields) {
            UUID timeBookId = UUID.fromString(item.get("timeId").toString());
            TimeBookDetail existingTime = timeBookRepository.findById(timeBookId).get();
            item.forEach((key,value)->{
                Field field = ReflectionUtils.findField(TimeBookDetail.class,key);
                field.setAccessible(true);
                System.out.println("fields: " + field);
                assert field != null;
                if(field.getName().equals("timeId")){
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingTime,timeBookId);
                } else if (field.getName().equals("start_time")) {
                    LocalTime start_time = LocalTime.parse(item.get("start_time").toString());
//                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingTime,start_time);
                } else if (field.getName().equals("end_time")) {
                    LocalTime end_time = LocalTime.parse(item.get("end_time").toString());
//                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingTime,end_time);
                } else if (field.getName().equals("isDeleted")) {
//                    field.setAccessible(true);
                    Boolean isDeleted = Boolean.valueOf(item.get("isDeleted").toString());
                    ReflectionUtils.setField(field,existingTime,isDeleted);
                } else {
//                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingTime,value);
                }
                System.out.println("key time: " + key + " value time: " + value);
            });
            timeBookRepository.save(existingTime);
        }
        System.out.println("OK");
    }
}
