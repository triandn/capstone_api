package com.example.capstone_be.service;

import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.model.ImageDetail;

import java.util.List;

public interface ImageService {
    List<ImageDto> createImageDetailForTour(List<ImageDto> imageDtos);
}
