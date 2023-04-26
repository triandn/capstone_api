package com.example.capstone_be.service;

import com.example.capstone_be.dto.category.CategoryDto;
import com.example.capstone_be.dto.tour.TourByCategoryDto;
import com.example.capstone_be.dto.tour.TourDetailDto;
import com.example.capstone_be.dto.tour.TourDto;
import com.example.capstone_be.dto.tour.TourViewDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.Category;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.repository.TourRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {

    private final ModelMapper mapper;
    private final TourRepository tourRepository;
    private Set<Category> categories;
    private Set<Category> cate;

    public TourServiceImpl(ModelMapper mapper, TourRepository tourRepository) {
        this.mapper = mapper;
        this.tourRepository = tourRepository;
    }

    @Override
    @Transactional
    public TourDto createTour(TourDto tourDto) {
        tourRepository.save(mapper.map(tourDto, Tour.class));
        return tourDto;
    }

    @Override
    public List<TourViewDto> getAll() {
        final List<Tour> tourList = tourRepository.findAll();
        List<TourViewDto> tourViewDtos = new ArrayList<>();
        TourViewDto tourViewDto = null;

        for (Tour tour: tourList) {
            tourViewDto = new TourViewDto();
            tourViewDto.setTourId(tour.getTourId());
            tourViewDto.setTitle(tour.getTitle());
            tourViewDto.setRating(tour.getRating());
            tourViewDto.setCity(tour.getCity());
            tourViewDto.setPriceOnePerson(tour.getPriceOnePerson());
            tourViewDto.setWorking(tour.getWorking());
            tourViewDto.setDestination(tour.getDestination());
            tourViewDto.setDestinationDescription(tour.getDestinationDescription());
            tourViewDto.setCategoryId(tour.getCategories().iterator().next().getCategoryId());
            tourViewDto.setCategoryName(tour.getCategories().iterator().next().getCategoryName().toString());
            tourViewDtos.add(tourViewDto);
        }
        return tourViewDtos;
    }

    @Override
    public List<TourByCategoryDto> getTourByCategoryName(String categoryName) {
        final List<Tour> tourList = tourRepository.findAll();
        List<TourByCategoryDto> result = new ArrayList<>();
        TourByCategoryDto tourByCategoryDto = null;
        for (Tour tour : tourList) {
            tourByCategoryDto = new TourByCategoryDto();
            if(tour.getCategories().iterator().next().getCategoryName().toLowerCase().toString().equals(categoryName.toLowerCase())){
                tourByCategoryDto.setTourId(tour.getTourId());
                tourByCategoryDto.setTitle(tour.getTitle());
                tourByCategoryDto.setRating(tour.getRating());
                tourByCategoryDto.setCity(tour.getCity());
                tourByCategoryDto.setPriceOnePerson(tour.getPriceOnePerson());
                tourByCategoryDto.setWorking(tour.getWorking());
                tourByCategoryDto.setDestination(tour.getDestination());
                tourByCategoryDto.setDestinationDescription(tour.getDestinationDescription());
                result.add(tourByCategoryDto);
            }
        }
        return result;
    }

    @Override
    public TourDetailDto getTourDetail(Long tourId) {
        final Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new NotFoundException("Tour not found !!"));
        System.out.println("Tour Detai: " + tour.toString());
        TourDetailDto tourDetailDto = null;
        tourDetailDto = new TourDetailDto();
        tourDetailDto.setTourId(tour.getTourId());
        tourDetailDto.setTitle(tour.getTitle());
        tourDetailDto.setRating(tour.getRating());
        tourDetailDto.setCity(tour.getCity());
        tourDetailDto.setPriceOnePerson(tour.getPriceOnePerson());
        tourDetailDto.setWorking(tour.getWorking());
        tourDetailDto.setDestination(tour.getDestination());
        tourDetailDto.setDestinationDescription(tour.getDestinationDescription());
        return tourDetailDto;
    }
}
