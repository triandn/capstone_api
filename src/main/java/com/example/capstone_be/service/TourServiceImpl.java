package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.*;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.image.ImageViewDto;
import com.example.capstone_be.dto.tour.*;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.*;
import com.example.capstone_be.repository.*;
import com.example.capstone_be.response.TourRespone;
import com.example.capstone_be.response.TourResponseByCategoryName;
import com.example.capstone_be.util.common.DeleteResponse;
import com.example.capstone_be.util.enums.RoleEnum;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {

    private final ModelMapper mapper;
    private final TourRepository tourRepository;
    private final ImageRepository imageRepository;
    private final ReviewService reviewService;
    private final ImageService imageService;
    private final DayBookService dayBookService;
    private final TimeBookDetailService timeBookDetailService;
    private final UserRepository userRepository;
    private final DayBookRepository dayBookRepository;
    private final TimeBookRepository timeBookRepository;

    private Set<Category> categories;
    private Set<Category> cate;

    public TourServiceImpl(ModelMapper mapper, TourRepository tourRepository, ImageRepository imageRepository, ReviewService reviewService, ImageService imageService, DayBookService dayBookService, TimeBookDetailService timeBookDetailService, UserRepository userRepository, DayBookRepository dayBookRepository, TimeBookRepository timeBookRepository) {
        this.mapper = mapper;
        this.tourRepository = tourRepository;
        this.imageRepository = imageRepository;
        this.reviewService = reviewService;
        this.imageService = imageService;
        this.dayBookService = dayBookService;
        this.timeBookDetailService = timeBookDetailService;
        this.userRepository = userRepository;
        this.dayBookRepository = dayBookRepository;
        this.timeBookRepository = timeBookRepository;
    }

    @Override
    @Transactional
    public TourCreateDto createTour(TourCreateDto tourDto, UUID user_id) {
        List<Tour> tourList = tourRepository.getAllTourByUserId(user_id);
        if(tourList.isEmpty()){
            userRepository.updateRole(user_id, RoleEnum.OWNER.toString());
        }
        Random random = new Random();
        int randomInt = random.nextInt();
        int nonNegativeInt = Math.abs(randomInt);

        Long tourId = (long) nonNegativeInt;
        TourCreateDto tourDtoCreate = new TourCreateDto();
        tourDtoCreate.setTourId(tourId);
        tourDtoCreate.setUserId(user_id);
        tourDtoCreate.setCategories(tourDto.getCategories());
        tourDtoCreate.setCity(tourDto.getCity());
        tourDtoCreate.setDestination(tourDto.getDestination());
        tourDtoCreate.setDestinationDescription(tourDto.getDestinationDescription());
        tourDtoCreate.setRating(tourDto.getRating());
        tourDtoCreate.setLatitude(tourDto.getLatitude());
        tourDtoCreate.setLongitude(tourDto.getLongitude());
        tourDtoCreate.setTitle(tourDto.getTitle());
        tourDtoCreate.setWorking(tourDto.getWorking());
        tourDtoCreate.setImageMain(tourDto.getImageMain());
        tourDtoCreate.setCheckIn(tourDto.getCheckIn());
        tourDtoCreate.setCheckOut(tourDto.getCheckOut());
        tourDtoCreate.setTimeSlotLength(tourDto.getTimeSlotLength());
        tourDtoCreate.setPriceOnePerson(tourDto.getPriceOnePerson());

// IMAGE PROCESS
        List<ImageDto> imageDtos = new ArrayList<>();
        List<ImageDto> imageDtoListFromRequest = tourDto.getImageDtoList();
        ImageDto imageDto = null;
        for (ImageDto item: imageDtoListFromRequest) {
            imageDto = new ImageDto();
            imageDto.setTourId(tourId);
            imageDto.setLink(item.getLink());
            imageDtos.add(imageDto);
        }
        tourRepository.save(mapper.map(tourDtoCreate, Tour.class));
        imageService.createImageDetailForTour(imageDtos);
// DATE PROCESS
        List<DateTime> dateTimes = getDateRange(tourDto.getStartDay(),tourDto.getEndDay());
        List<DateBookCreateDto> dateBookCreateDtos = new ArrayList<>();
        DateBookCreateDto dateBookCreateDto = null;
        for (DateTime item: dateTimes) {
            dateBookCreateDto = new DateBookCreateDto();
            dateBookCreateDto.setDate_name(item.toDate());
            dateBookCreateDto.setTourId(tourId);
            dateBookCreateDtos.add(dateBookCreateDto);
        }
// TIME PROCESS
        List<LocalTime> localTimes = DivideFrameTime(tourDto.getTimeBookStart(),tourDto.getTimeBookEnd(),tourDto.getTimeSlotLength());
        System.out.println("THOI GIAN CHIA: " + localTimes.size());
        List<TimeBookDetailDto> timeBookDetailDtoList = null;
        List<TimeBookDetail> timeBookDetailList=null;
        TimeBookDetailDto timeBookDetailDto = null;
        TimeBookDetail timeBookDetail = null;
        for (DateBookCreateDto item: dateBookCreateDtos) {
            dayBookService.createDayBooking(item);
            timeBookDetailDtoList = new ArrayList<>();
            timeBookDetailList = new ArrayList<>();
            System.out.println("DayBook Id:"+item.getDayBookId());
            for (int index = 0; index <= localTimes.size(); index++){
                if(index + 1 >= localTimes.size()){
                    break;
                }
//                timeBookDetail = new TimeBookDetail();
//                timeBookDetail.setDay_book_id(item.getDayBookId());
//                timeBookDetail.setStart_time(localTimes.get(index));
//                timeBookDetail.setEnd_time(localTimes.get(index + 1));
//                timeBookDetailList.add(timeBookDetail);

                timeBookDetailDto = new TimeBookDetailDto();
                timeBookDetailDto.setDay_book_id(item.getDayBookId());
                timeBookDetailDto.setStart_time(localTimes.get(index));
                timeBookDetailDto.setEnd_time(localTimes.get(index + 1));
                timeBookDetailDtoList.add(timeBookDetailDto);
                timeBookDetailService.createTimeBookDetail(timeBookDetailDto);
            }
        }
//        timeBookRepository.saveAll(timeBookDetailList);

        return tourDto;
    }

    @Override
    public TourRespone getAll(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo - 1, pageSize);
        Page<Tour> tourList = tourRepository.findAll(paging);
        final TourRespone tourRespone = new TourRespone();
        List<TourViewDto> tourViewDtos = new ArrayList<>();
        TourViewDto tourViewDto = null;
        Double avgRating = 0.0;
        for (Tour tour: tourList) {
            tourViewDto = new TourViewDto();
            avgRating = reviewService.calAvgRatingReviewForTour(tour.getTourId());
            tourViewDto.setTourId(tour.getTourId());
            tourViewDto.setTitle(tour.getTitle());
            tourViewDto.setRating(tour.getRating());
            tourViewDto.setCity(tour.getCity());
            tourViewDto.setPriceOnePerson(tour.getPriceOnePerson());
            tourViewDto.setWorking(tour.getWorking());
            tourViewDto.setLatitude(tour.getLatitude());
            tourViewDto.setLongitude(tour.getLongitude());
            tourViewDto.setDestination(tour.getDestination());
            tourViewDto.setDestinationDescription(tour.getDestinationDescription());
            tourViewDto.setCategoryId(tour.getCategories().iterator().next().getCategoryId());
            tourViewDto.setCategoryName(tour.getCategories().iterator().next().getCategoryName().toString());
            tourViewDto.setAvgRating(avgRating);
            tourViewDto.setUserId(tour.getUserId());
            tourViewDto.setImageMain(tour.getImageMain());
            tourViewDto.setTimeSlotLength(tour.getTimeSlotLength());
            tourViewDto.setIsDeleted(tour.getIsDeleted());
            tourViewDtos.add(tourViewDto);
        }
        tourRespone.setContent(tourViewDtos);
        tourRespone.setPageNo(tourList.getNumber() + 1);
        tourRespone.setPageSize(tourList.getSize());
        tourRespone.setTotalElements(tourList.getTotalElements());
        tourRespone.setTotalPages(tourList.getTotalPages());
        return tourRespone;
    }

    @Override
    public TourResponseByCategoryName getTourByCategoryName(String categoryName,Integer pageNo, Integer pageSize) {

        Pageable paging = PageRequest.of(pageNo - 1, pageSize); // paging

        Page<Tour> tourListByCategoryName = tourRepository.findTourByCategoryName(categoryName,paging);

        TourResponseByCategoryName tourResponseByCategoryName = new TourResponseByCategoryName();
        List<TourByCategoryDto> tourByCategoryDtos = new ArrayList<>();
        TourByCategoryDto tourByCategoryDto = null;
        Double avgRating = 0.0;
        System.out.println("Tour List Size: " + tourListByCategoryName.getSize());
        for (Tour tour : tourListByCategoryName.getContent()) {
            tourByCategoryDto = new TourByCategoryDto();
            avgRating = reviewService.calAvgRatingReviewForTour(tour.getTourId());
            tourByCategoryDto.setTourId(tour.getTourId());
            tourByCategoryDto.setTitle(tour.getTitle());
            tourByCategoryDto.setRating(tour.getRating());
            tourByCategoryDto.setCity(tour.getCity());
            tourByCategoryDto.setPriceOnePerson(tour.getPriceOnePerson());
            tourByCategoryDto.setImageMain(tour.getImageMain());
            tourByCategoryDto.setWorking(tour.getWorking());
            tourByCategoryDto.setLatitude(tour.getLatitude());
            tourByCategoryDto.setLongitude(tour.getLongitude());
            tourByCategoryDto.setDestination(tour.getDestination());
            tourByCategoryDto.setDestinationDescription(tour.getDestinationDescription());
            tourByCategoryDto.setAvgRating(avgRating);
            tourByCategoryDto.setUserId(tour.getUserId());
            tourByCategoryDto.setTimeSlotLength(tour.getTimeSlotLength());
            tourByCategoryDtos.add(tourByCategoryDto);
        }
        System.out.println("Tour By Category Name Size: " + tourByCategoryDtos.size());
        tourResponseByCategoryName.setContent(tourByCategoryDtos);
        tourResponseByCategoryName.setPageNo(tourListByCategoryName.getNumber() + 1);
        tourResponseByCategoryName.setPageSize(tourListByCategoryName.getSize());
        tourResponseByCategoryName.setTotalElements(tourListByCategoryName.getTotalElements());
        tourResponseByCategoryName.setTotalPages(tourListByCategoryName.getTotalPages());
        return tourResponseByCategoryName;
    }

    @Override
    public TourDetailDto getTourDetail(Long tourId) {
        final Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new NotFoundException("Tour not found !!"));
        List<ImageViewDto> imageViewDtos = new ArrayList<>();
        List<ImageDetail> imageDetails = imageRepository.getImageDetailByTourId(tourId);
        Double avgRatingTour = reviewService.calAvgRatingReviewForTour(tour.getTourId());
        for (ImageDetail image: imageDetails) {
            imageViewDtos.add(mapper.map(image,ImageViewDto.class));
        }
        TourDetailDto tourDetailDto = null;
        tourDetailDto = new TourDetailDto();
        tourDetailDto.setTourId(tour.getTourId());
        tourDetailDto.setTitle(tour.getTitle());
        tourDetailDto.setRating(tour.getRating());
        tourDetailDto.setCity(tour.getCity());
        tourDetailDto.setPriceOnePerson(tour.getPriceOnePerson());
        tourDetailDto.setImageMain(tour.getImageMain());
        tourDetailDto.setWorking(tour.getWorking());
        tourDetailDto.setLatitude(tour.getLatitude());
        tourDetailDto.setLongitude(tour.getLongitude());
        tourDetailDto.setDestination(tour.getDestination());
        tourDetailDto.setDestinationDescription(tour.getDestinationDescription());
        tourDetailDto.setImages(imageViewDtos);
        tourDetailDto.setAvgRating(avgRatingTour);
        tourDetailDto.setUserId(tour.getUserId());
        tourDetailDto.setTimeSlotLength(tour.getTimeSlotLength());
        tourDetailDto.setIsDeleted(tour.getIsDeleted());
        return tourDetailDto;
    }

    @Override
    public ResponseEntity<?> deleteByTourId(Long id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new NotFoundException("TimeBooking not found"));
        if(tour.getIsDeleted().equals(true)){
            return new ResponseEntity<>(new DeleteResponse("THIS TOUR IS DELETED"), HttpStatus.NOT_FOUND);
        }
        tour.setIsDeleted(true);
        tourRepository.save(tour);
        return new ResponseEntity<>(new DeleteResponse("DELETE SUCCESS"),HttpStatus.OK);
    }

    @Override
    public TourDto updateByTourId(TourDto tourDto, Long id) {
        final Tour updatedTour = tourRepository.findById(id)
                .map(tour -> {
                    tour.setTourId(tourDto.getTourId());
                    tour.setTitle(tourDto.getTitle());
                    tour.setCity(tourDto.getCity());
                    tour.setPriceOnePerson(tourDto.getPriceOnePerson());
                    tour.setImageMain(tourDto.getImageMain());
                    tour.setWorking(tourDto.getWorking());
                    tour.setLatitude(tourDto.getLatitude());
                    tour.setLongitude(tourDto.getLongitude());
                    tour.setDestination(tourDto.getDestination());
                    tour.setDestinationDescription(tourDto.getDestinationDescription());
                    tour.setTimeSlotLength(tourDto.getTimeSlotLength());
                    return tourRepository.save(tour);
                })
                .orElseGet(() -> {
                    tourDto.setTourId(id);
                    return tourRepository.save(mapper.map(tourDto, Tour.class));
                });

        return mapper.map(updatedTour, TourDto.class);
    }

    @Override
    public List<TourViewByUserDto> getTourByUserId(UUID userId) {
        List<Tour> tourListByUserId = tourRepository.getTourByUserId(userId);
        TourViewByUserDto tourViewByUserDto = null;
        List<TourViewByUserDto> tourViewByUserDtoList = new ArrayList<>();
        for (Tour tour: tourListByUserId) {
            tourViewByUserDto = new TourViewByUserDto();
            tourViewByUserDto.setTourId(tour.getTourId());
            tourViewByUserDto.setTitle(tour.getTitle());
            tourViewByUserDto.setCity(tour.getCity());
            tourViewByUserDto.setPriceOnePerson(tour.getPriceOnePerson());
            tourViewByUserDto.setImageMain(tour.getImageMain());
            tourViewByUserDto.setWorking(tour.getWorking());
            tourViewByUserDto.setLatitude(tour.getLatitude());
            tourViewByUserDto.setLongitude(tour.getLongitude());
            tourViewByUserDto.setDestination(tour.getDestination());
            tourViewByUserDto.setDestinationDescription(tour.getDestinationDescription());
            tourViewByUserDto.setTimeSlotLength(tour.getTimeSlotLength());
            tourViewByUserDto.setCategoryId(tour.getCategories().iterator().next().getCategoryId());
            tourViewByUserDto.setCategoryName(tour.getCategories().iterator().next().getCategoryName());
            List<ImageDetail> imageDetailList = imageRepository.getImageDetailByTourId(tour.getTourId());
            tourViewByUserDto.setImageDtoList(imageDetailList.stream().map(prize -> mapper.map(prize, ImageViewDto.class)).collect(Collectors.toList()));

            //DAYBOOK PROCESS
            List<DayBook> dayBookList = dayBookRepository.getDayBookByTourId(tour.getTourId());
            List<DayBookViewDto> dayBookViewDtoList = new ArrayList<>();

            for (DayBook dayBook: dayBookList) {
                DayBookViewDto dayBookViewDto = new DayBookViewDto();
                dayBookViewDto.setDate_name(dayBook.getDate_name());
                dayBookViewDto.setDayBookId(dayBook.getDayBookId());
                dayBookViewDto.setStatus(dayBook.getStatus());
                dayBookViewDto.setTourId(dayBook.getTourId());
                dayBookViewDto.setStatus(dayBook.getStatus());
                dayBookViewDto.setIs_deleted(dayBook.getIsDeleted());
                List<TimeBookViewDto> timeBookViewDtoList = timeBookDetailService.getAllTimeBookForDayByDayBookId(dayBook.getDayBookId());
                dayBookViewDto.setTimeBookViewDtoList(timeBookViewDtoList);
                dayBookViewDtoList.add(dayBookViewDto);
            }
            tourViewByUserDto.setDayBookList(dayBookViewDtoList);
            tourViewByUserDtoList.add(tourViewByUserDto);
        }
        return tourViewByUserDtoList;
    }

    public static List<DateTime> getDateRange(DateTime startDay, DateTime endDay) {
        List<DateTime> ret = new ArrayList<DateTime>();
        DateTime tmp = startDay;
        while(tmp.isBefore(endDay) || tmp.equals(endDay)) {
            ret.add(tmp);
            tmp = tmp.plusDays(1);
        }
        return ret;
    }

    public  static List<LocalTime> DivideFrameTime(TimeBookStart timeBookStart, TimeBookEnd timeBookEnd, int timeSlotLength){
        LocalTime startTime = LocalTime.of(timeBookStart.getHour(),timeBookEnd.getMinutes()); // Thời gian bắt đầu
        LocalTime endTime = LocalTime.of(timeBookEnd.getHour(), timeBookEnd.getMinutes()); // Thời gian kết thúc
        Duration interval = Duration.ofMinutes(timeSlotLength); // Khoảng thời gian chia
        List<LocalTime> localTimes = new ArrayList<>();
        LocalTime currentTime = startTime;
        while (currentTime.isBefore(endTime)) {
            System.out.println(currentTime);
            localTimes.add(currentTime);
            currentTime = currentTime.plus(interval);
        }
        localTimes.add(endTime);
        for (LocalTime item: localTimes) {
            System.out.println("CHIA THOI GIAN THEO DOAN: "+ item);
        }
        return localTimes;
    }

}
