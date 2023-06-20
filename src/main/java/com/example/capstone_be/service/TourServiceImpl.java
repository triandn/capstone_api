package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.*;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.image.ImageViewDto;
import com.example.capstone_be.dto.order.OrderDto;
import com.example.capstone_be.dto.tour.*;
import com.example.capstone_be.dto.user.UserViewDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.*;
import com.example.capstone_be.repository.*;
import com.example.capstone_be.response.TourRespone;
import com.example.capstone_be.response.TourResponseByCategoryName;
import com.example.capstone_be.response.TourResponseByOwner;
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
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

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
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private Set<Category> categories;
    private Set<Category> cate;

    public TourServiceImpl(ModelMapper mapper, TourRepository tourRepository, ImageRepository imageRepository, ReviewService reviewService, ImageService imageService, DayBookService dayBookService, TimeBookDetailService timeBookDetailService, UserRepository userRepository, DayBookRepository dayBookRepository, TimeBookRepository timeBookRepository, OrderService orderService, OrderRepository orderRepository) {
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
        this.orderService = orderService;
        this.orderRepository = orderRepository;
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
        // START TIME AND END TIME
        LocalTime startTime = LocalTime.of(tourDto.getTimeBookStart().getHour(),tourDto.getTimeBookStart().getMinutes()); // Thời gian bắt đầu
        LocalTime endTime = LocalTime.of(tourDto.getTimeBookEnd().getHour(), tourDto.getTimeBookEnd().getMinutes()); // Thời gian kết thúc

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
        tourDtoCreate.setUserId(user_id);

        Tour tourSave = new Tour();
        tourSave.setTourId(tourId);
        tourSave.setUserId(user_id);
        tourSave.setCategories(tourDto.getCategories());
        tourSave.setCity(tourDto.getCity());
        tourSave.setDestination(tourDto.getDestination());
        tourSave.setDestinationDescription(tourDto.getDestinationDescription());
        tourSave.setRating(tourDto.getRating());
        tourSave.setLatitude(Float.valueOf(tourDto.getLatitude()));
        tourSave.setLongitude(Float.valueOf(tourDto.getLongitude()));
        tourSave.setTitle(tourDto.getTitle());
        tourSave.setWorking(tourDto.getWorking());
        tourSave.setImageMain(tourDto.getImageMain());
        tourSave.setCheckIn(tourDto.getCheckIn());
        tourSave.setCheckOut(tourDto.getCheckOut());
        tourSave.setTimeSlotLength(tourDto.getTimeSlotLength());
        tourSave.setPriceOnePerson(Double.valueOf(tourDto.getPriceOnePerson()));
        tourSave.setTimeBookStart(startTime);
        tourSave.setTimeBookEnd(endTime);
        tourSave.setUserId(user_id);

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
//        tourRepository.save(mapper.map(tourDtoCreate, Tour.class));
        tourRepository.save(tourSave);
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
                timeBookDetailDto = new TimeBookDetailDto();
                timeBookDetailDto.setDay_book_id(item.getDayBookId());
                timeBookDetailDto.setStart_time(localTimes.get(index));
                timeBookDetailDto.setEnd_time(localTimes.get(index + 1));
                timeBookDetailDtoList.add(timeBookDetailDto);
                timeBookDetailService.createTimeBookDetail(timeBookDetailDto);
            }
        }
        return tourDto;
    }

    @Override
    public TourRespone getAll(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo - 1, pageSize);
        Page<Tour> tourList = tourRepository.getAllTour(paging);
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
    public List<TourViewForChatGPT> getAllForChatGPT() {
        List<Tour> tours = tourRepository.getAllTourForChatGpt();
        TourViewForChatGPT tourViewForChatGPT = null;
        List<TourViewForChatGPT> tourViewForChatGPTList = new ArrayList<>();
        Double avgRating =0.0;
        for (Tour item: tours) {
            avgRating = reviewService.calAvgRatingReviewForTour(item.getTourId());
            tourViewForChatGPT = new TourViewForChatGPT();
            tourViewForChatGPT.setTourId(item.getTourId());
            tourViewForChatGPT.setCity(item.getCity());
            tourViewForChatGPT.setDestination(item.getDestination());
            tourViewForChatGPT.setCategoryName(item.getCategories().iterator().next().getCategoryName());
            tourViewForChatGPT.setCategoryId(item.getCategories().iterator().next().getCategoryId());
            tourViewForChatGPT.setWorking(item.getWorking());
            tourViewForChatGPT.setDestinationDescription(item.getDestinationDescription());
            tourViewForChatGPT.setPriceOnePerson(item.getPriceOnePerson());
            tourViewForChatGPT.setAvgRating(avgRating);
            tourViewForChatGPT.setImageMain(item.getImageMain());
            tourViewForChatGPT.setTitle(item.getTitle());
            tourViewForChatGPTList.add(tourViewForChatGPT);
        }
        return tourViewForChatGPTList;
    }

    @Override
    public TourResponseByCategoryName getTourByCategoryName(String categoryName,Integer pageNo,
                                                            Integer pageSize,
                                                            String northEastLat,
                                                            String northEastLng,
                                                            String southWestLat,
                                                            String southWestLng) {

        Pageable paging = PageRequest.of(pageNo - 1, pageSize); // paging

        Page<Tour> tourListByCategoryName = tourRepository.findTourByCategoryName(categoryName,
                Float.valueOf(northEastLat),
                Float.valueOf(southWestLat),
                Float.valueOf(northEastLng),
                Float.valueOf(southWestLng),
                paging);

        TourResponseByCategoryName tourResponseByCategoryName = new TourResponseByCategoryName();
        List<TourViewDto> tourByCategoryDtos = new ArrayList<>();
        TourViewDto tourByCategoryDto = null;
        Double avgRating = 0.0;
        System.out.println("Tour List Size: " + tourListByCategoryName.getContent().size());
        for (Tour tour : tourListByCategoryName.getContent()) {
            tourByCategoryDto = new TourViewDto();
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
            tourByCategoryDto.setCategoryId(tour.getCategories().iterator().next().getCategoryId());
            tourByCategoryDto.setCategoryName(tour.getCategories().iterator().next().getCategoryName());
            tourByCategoryDto.setIsDeleted(tour.getIsDeleted());
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
        TimeBookStart timeBookStart = new TimeBookStart();
        TimeBookEnd timeBookEnd = new TimeBookEnd();

        User user = userRepository.getUserByTourId(tourId);
        UserViewDto userViewDto = new UserViewDto();
        userViewDto.setUserId(user.getUserId());
        userViewDto.setRole(user.getRole());
        userViewDto.setAddress(user.getAddress());
        userViewDto.setLanguage(user.getLanguage());
        userViewDto.setUserName(user.getUserName());
        userViewDto.setUserEmail(user.getUserEmail());
        userViewDto.setUrlImage(user.getUrlImage());
        userViewDto.setDescription(user.getDescription());
        userViewDto.setPhoneNumber(user.getPhoneNumber());

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
        tourDetailDto.setUser(userViewDto);
        try{
            timeBookStart.setHour(tour.getTimeBookStart().getHour());
            timeBookStart.setMinutes(tour.getTimeBookStart().getMinute());

            timeBookEnd.setHour(tour.getTimeBookEnd().getHour());
            timeBookEnd.setMinutes(tour.getTimeBookEnd().getMinute());

            tourDetailDto.setTimeBookStart(timeBookStart);
            tourDetailDto.setTimeBookEnd(timeBookEnd);
        }
        catch (Exception e){
            System.out.println("Error" + e);
        }

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
    public Tour updateTourByField(Long id,Map<String, Object> fields) {
        Optional<Tour> existingTour = tourRepository.findById(id);
        if(existingTour.isPresent()){
            fields.forEach((key,value)->{
                Field field = ReflectionUtils.findField(Tour.class,key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,existingTour.get(),value);
            });
            return tourRepository.save(existingTour.get());
        }
        return null;
    }

    @Override
    public TourResponseByOwner getTourByUserId(UUID userId,Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo - 1, pageSize); // paging
        Page<Tour> tourListByUserId = tourRepository.getTourByUserId(userId,paging);
        TourViewByUserDto tourViewByUserDto = null;
        List<TourViewByUserDto> tourViewByUserDtoList = new ArrayList<>();

        TourResponseByOwner tourResponseByOwner = new TourResponseByOwner();
        for (Tour tour: tourListByUserId) {
            tourViewByUserDto = new TourViewByUserDto();
            List<OrderDto> orderDtoList = new ArrayList<>();
            orderDtoList = orderService.getListOrderByTourId(tour.getTourId());
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
            tourViewByUserDto.setIsDeleted(tour.getIsDeleted());
            tourViewByUserDto.setOrderDtoList(orderDtoList);
            tourViewByUserDtoList.add(tourViewByUserDto);
        }
        tourResponseByOwner.setContent(tourViewByUserDtoList);
        tourResponseByOwner.setPageNo(tourListByUserId.getNumber() + 1);
        tourResponseByOwner.setPageSize(tourListByUserId.getSize());
        tourResponseByOwner.setTotalElements(tourListByUserId.getTotalElements());
        tourResponseByOwner.setTotalPages(tourListByUserId.getTotalPages());
        return tourResponseByOwner;
    }

    @Override
    @Transactional
    public void updateTimeTour(UpdateTimeTourDto updateTimeTourDto, Long tourId) {

        timeBookRepository.deleteListTimeByTourId(tourId);

        LocalTime startTime = LocalTime.of(updateTimeTourDto.getTimeBookStart().getHour(),updateTimeTourDto.getTimeBookStart().getMinutes()); // Thời gian bắt đầu
        LocalTime endTime = LocalTime.of(updateTimeTourDto.getTimeBookEnd().getHour(), updateTimeTourDto.getTimeBookEnd().getMinutes()); // Thời gian kết thúc
        tourRepository.updateStartTimeAndEndTime(startTime,endTime,updateTimeTourDto.getTimeSlotLength(),tourId);

        Tour tour = tourRepository.getTourById(tourId);
        List<DayBook> dayBookList = dayBookRepository.getDayBookByTourId(tourId);
        List<LocalTime> localTimes = DivideFrameTime(updateTimeTourDto.getTimeBookStart(),updateTimeTourDto.getTimeBookEnd(),updateTimeTourDto.getTimeSlotLength());
        System.out.println("THOI GIAN CHIA: " + localTimes.size());

        List<TimeBookDetailDto> timeBookDetailDtoList = null;
        TimeBookDetailDto timeBookDetailDto = null;
        for (DayBook item: dayBookList) {
            timeBookDetailDtoList = new ArrayList<>();
            System.out.println("DayBook Id:"+item.getDayBookId());
            for (int index = 0; index <= localTimes.size(); index++){
                if(index + 1 >= localTimes.size()){
                    break;
                }
                timeBookDetailDto = new TimeBookDetailDto();
                timeBookDetailDto.setDay_book_id(item.getDayBookId());
                timeBookDetailDto.setStart_time(localTimes.get(index));
                timeBookDetailDto.setEnd_time(localTimes.get(index + 1));
                timeBookDetailDtoList.add(timeBookDetailDto);
                timeBookDetailService.createTimeBookDetail(timeBookDetailDto);
            }
        }
    }

    @Override
    public TourRespone getTourViewPort(String northEastLat, String southWestLat, String northEastLng, String southWestLng,Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo - 1, pageSize);
        Page<Tour> tourList = tourRepository.getTourViewPort(northEastLat,southWestLat,northEastLng,southWestLng,paging);
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
