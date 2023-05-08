package com.example.capstone_be.service;

import com.example.capstone_be.dto.image.ImageViewDto;
import com.example.capstone_be.dto.tour.TourByCategoryDto;
import com.example.capstone_be.dto.tour.TourDetailDto;
import com.example.capstone_be.dto.tour.TourDto;
import com.example.capstone_be.dto.tour.TourViewDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.Category;
import com.example.capstone_be.model.ImageDetail;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.repository.ImageRepository;
import com.example.capstone_be.repository.TourRepository;
import com.example.capstone_be.response.TourRespone;
import com.example.capstone_be.response.TourResponseByCategoryName;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TourServiceImpl implements TourService {

    private final ModelMapper mapper;
    private final TourRepository tourRepository;

    private final ImageRepository imageRepository;

    private final ReviewService reviewService;
    private Set<Category> categories;
    private Set<Category> cate;

    public TourServiceImpl(ModelMapper mapper, TourRepository tourRepository, ImageRepository imageRepository, ReviewService reviewService) {
        this.mapper = mapper;
        this.tourRepository = tourRepository;
        this.imageRepository = imageRepository;
        this.reviewService = reviewService;
    }

    @Override
    @Transactional
    public TourDto createTour(TourDto tourDto) {
        tourRepository.save(mapper.map(tourDto, Tour.class));
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

        return tourDetailDto;
    }

    @Override
    public void deleteByTourId(Long id) {

        Tour tour = tourRepository.findById(id).orElseThrow(() -> new NotFoundException("Tour not found"));
        tourRepository.deleteById(id);
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
                    return tourRepository.save(tour);
                })
                .orElseGet(() -> {
                    tourDto.setTourId(id);
                    return tourRepository.save(mapper.map(tourDto, Tour.class));
                });

        return mapper.map(updatedTour, TourDto.class);
    }
}
