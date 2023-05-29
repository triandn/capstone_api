package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.*;
import com.example.capstone_be.exception.ComparisonException;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.DayBook;
import com.example.capstone_be.model.TimeBookDetail;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.repository.DayBookRepository;
import com.example.capstone_be.response.DayBookPagingResponse;
import com.example.capstone_be.response.DayPagingResponse;
import com.example.capstone_be.util.common.DeleteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
        for (DayBook daybook: dayBookList) {
            dayBookDtoList.add(mapper.map(daybook,DayBookDto.class));
        }
        return dayBookDtoList;
    }

    @Override
    @Transactional
    public DateBookCreateDto createDayBooking(DateBookCreateDto dayBookDto) {
        dayBookRepository.save(mapper.map(dayBookDto,DayBook.class));
        return dayBookDto;
    }

    @Override
    public ResponseEntity<?> deleteByDayBookId(UUID id) {
            DayBook dayBook = dayBookRepository.findById(id).orElseThrow(() -> new NotFoundException("DayBooking not found"));
            if(dayBook.getIsDeleted().equals(true)){
                return new ResponseEntity<>(new DeleteResponse("THIS DAYBOOK IS DELETED"), HttpStatus.NOT_FOUND);
            }
            dayBook.setIsDeleted(true);
            dayBookRepository.save(dayBook);
            return new ResponseEntity<>(new DeleteResponse("Delete Success"), HttpStatus.OK);
    }


    @Override
    public DayBookViewDto getDayBookingById(UUID id) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DayBook dayBook = dayBookRepository.findById(id).orElseThrow(() -> new NotFoundException("DayBooking not found"));
        DayBookViewDto dayBookDto = new DayBookViewDto();
        List<TimeBookViewDto> timeBookViewDtoList = timeBookDetailService.getAllTimeBookForDayByDayBookId(id);
        dayBookDto.setTourId(dayBook.getTourId());
        dayBookDto.setDayBookId(dayBook.getDayBookId());
        dayBookDto.setDate_name(formatter.format(dayBook.getDate_name()).toString());
        dayBookDto.setStatus(dayBook.getStatus());
        dayBookDto.setTimeBookDetailList(timeBookViewDtoList);
        dayBookDto.setIs_deleted(dayBook.getIsDeleted());

        return dayBookDto;
    }

    @Override
    public DayBookPagingResponse getDayAndTimeByTourId(Long tourId,String start,String end,Integer pageNo, Integer pageSize) throws ParseException {
//        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(start);
        Date endDate = formatter.parse(end);
        Pageable paging = PageRequest.of(pageNo - 1, pageSize); // paging

        Page<DayBook> dayBookList = dayBookRepository.getDayBookByTourIdPaging(tourId,startDate,endDate,paging);
        List<DayBookViewDto> dayBookViewDtoList = new ArrayList<>();
        DayBookPagingResponse dayBookPagingResponse = new DayBookPagingResponse();

        for (DayBook dayBook: dayBookList) {
            DayBookViewDto dayBookViewDto = new DayBookViewDto();
            dayBookViewDto.setDate_name(formatter.format(dayBook.getDate_name()).toString());
            dayBookViewDto.setDayBookId(dayBook.getDayBookId());
            dayBookViewDto.setStatus(dayBook.getStatus());
            dayBookViewDto.setTourId(dayBook.getTourId());
            dayBookViewDto.setStatus(dayBook.getStatus());
            dayBookViewDto.setIs_deleted(dayBook.getIsDeleted());
            List<TimeBookViewDto> timeBookViewDtoList = timeBookDetailService.getAllTimeBookForDayByDayBookId(dayBook.getDayBookId());
            dayBookViewDto.setTimeBookDetailList(timeBookViewDtoList);
            dayBookViewDtoList.add(dayBookViewDto);
        }
        dayBookPagingResponse.setContent(dayBookViewDtoList);
        dayBookPagingResponse.setPageNo(dayBookList.getNumber() + 1);
        dayBookPagingResponse.setPageSize(dayBookList.getSize());
        dayBookPagingResponse.setTotalElements(dayBookList.getTotalElements());
        dayBookPagingResponse.setTotalPages(dayBookList.getTotalPages());
        return dayBookPagingResponse;
    }

    @Override
    public List<DayBookDto> getDayBookByTourId(Long tourId) {
        List<DayBook> dayBookList = dayBookRepository.getDayBookByTourId(tourId);
        return dayBookList.stream().map(prize -> mapper.map(prize, DayBookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DayPagingResponse getDayBookByTourIdPaging(Long tourId,String start,String end, Integer pageNo, Integer pageSize) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Date startDate = formatter.parse(start);
        Date endDate = formatter.parse(end);
        Pageable paging = PageRequest.of(pageNo - 1, pageSize); // paging
        Page<DayBook> dayBookList = dayBookRepository.getDayBookByTourIdPaging(tourId,startDate,endDate,paging);
        List<DayBookDto> dayBookViewDtoList = new ArrayList<>();
        DayPagingResponse dayPagingResponse = new DayPagingResponse();
        for (DayBook daybook: dayBookList) {
            DayBookDto dayBookDto = new DayBookDto();
            dayBookDto.setTourId(daybook.getTourId());
            dayBookDto.setStatus(daybook.getStatus());
            dayBookDto.setIsDeleted(daybook.getIsDeleted());
            dayBookDto.setDayBookId(daybook.getDayBookId());
            dayBookDto.setDate_name(formatter.format(daybook.getDate_name()).toString());
            dayBookViewDtoList.add(dayBookDto);
        }
        for (DayBookDto item: dayBookViewDtoList) {
            System.out.println("START DATE: " + item.getDate_name().toString() + "ID: " + item.getDayBookId());
        }
        dayPagingResponse.setContent(dayBookViewDtoList);
        dayPagingResponse.setPageNo(dayBookList.getNumber()+1);
        dayPagingResponse.setPageSize(dayBookList.getSize());
        dayPagingResponse.setTotalElements(dayBookList.getTotalElements());
        dayPagingResponse.setTotalPages(dayBookList.getTotalPages());
        return dayPagingResponse;
    }

    @Override
    public void updateDayByField(List<Map<String, Object>> fields) {
        for (Map<String,Object> item : fields) {
            UUID dayBookId = UUID.fromString(item.get("dayBookId").toString());
            DayBook existingDay = dayBookRepository.findById(dayBookId).get();
            DayBookUpdate dayBookUpdate = new DayBookUpdate();
            dayBookUpdate.setDate_name(existingDay.getDate_name());
            dayBookUpdate.setIsDeleted(existingDay.getIsDeleted());
            dayBookUpdate.setStatus(existingDay.getStatus());
            dayBookUpdate.setTourId(existingDay.getTourId());
            dayBookUpdate.setDayBookId(existingDay.getDayBookId());

            item.forEach((key,value)->{
                Field field = ReflectionUtils.findField(DayBookUpdate.class,key);
                field.setAccessible(true);
                assert field != null;
                if(field.getName().equals("dayBookId")){
                    ReflectionUtils.setField(field,dayBookUpdate,dayBookId);
                } else if (field.getName().equals("timeBookDetailList")) {
                    ReflectionUtils.setField(field,dayBookUpdate,value);
                    timeBookDetailService.updateTimeBookByField((List<Map<String, Object>>) value);
                } else if (field.getName().equals("date_name")) {
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    Date startDate;
                    try {
                        startDate = df.parse(item.get("date_name").toString());
                        String newDateString = df.format(startDate);
                        ReflectionUtils.setField(field,dayBookUpdate,newDateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (field.getName().equals("tourId")) {
                    Long tourId = Long.valueOf(item.get("tourId").toString());
                    ReflectionUtils.setField(field,dayBookUpdate,tourId);
                } else if (field.getName().equals("isDeleted")) {
                    Boolean isDeleted = Boolean.valueOf(item.get("isDeleted").toString());
                    ReflectionUtils.setField(field,dayBookUpdate,isDeleted);
                } else {
                    ReflectionUtils.setField(field,dayBookUpdate,value);
                }
//                System.out.println("key: " + key + " value: " + value);
            });
            dayBookRepository.save(mapper.map(dayBookUpdate, DayBook.class));
        }
    }
}
