package com.example.capstone_be.service;

import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.image.ImageViewDto;
import com.example.capstone_be.dto.tour.TourDto;
import com.example.capstone_be.model.ImageDetail;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public interface ImageService {
    List<ImageDto> createImageDetailForTour(List<ImageDto> imageDtos);

    void deleteByImageId(UUID id);

    ImageDto updateByImageId(ImageDto imageDto, UUID id);

    List<ImageViewDto> getAllImage();
}
