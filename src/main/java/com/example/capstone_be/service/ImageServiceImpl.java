package com.example.capstone_be.service;

import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.image.ImageViewDto;
import com.example.capstone_be.dto.tour.TourDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.ImageDetail;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.repository.ImageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private final ModelMapper mapper;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ModelMapper mapper, ImageRepository imageRepository) {
        this.mapper = mapper;
        this.imageRepository = imageRepository;
    }

    @Override
    @Transactional
    public List<ImageDto> createImageDetailForTour(List<ImageDto> imageDtos) {
        List<ImageDetail> imageDetailList = new ArrayList<>();
        for (ImageDto imageDto: imageDtos) {
            imageDetailList.add(mapper.map(imageDto,ImageDetail.class));
        }
        imageRepository.saveAll(imageDetailList);
        return imageDtos;
    }

    @Override
    public void deleteByImageId(UUID id) {
        ImageDetail image = imageRepository.findById(id).orElseThrow(() -> new NotFoundException("Image not found"));
        imageRepository.deleteById(id);
    }

    @Override
    public ImageDto updateByImageId(ImageDto imageDto, UUID id) {
        final ImageDetail updatedImageDetail = imageRepository.findById(id)
                .map(imageDetail -> {
                    imageDetail.setImageId(imageDto.getImageId());
                    imageDetail.setLink(imageDto.getLink());

                    return imageRepository.save(imageDetail);
                })
                .orElseGet(() -> {
                    imageDto.setImageId(id);
                    return imageRepository.save(mapper.map(imageDto, ImageDetail.class));
                });

        return mapper.map(updatedImageDetail, ImageDto.class);
    }

    @Override
    public List<ImageViewDto> getAllImage() {
        List<ImageDetail> imageDetailList = imageRepository.findAll();

        List<ImageViewDto> imageViewDtoList = new ArrayList<>();

        for (ImageDetail imageDetail: imageDetailList) {
            imageViewDtoList.add(mapper.map(imageDetail, ImageViewDto.class));
        }
        return imageViewDtoList;
    }
}
