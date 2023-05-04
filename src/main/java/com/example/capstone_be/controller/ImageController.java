package com.example.capstone_be.controller;


import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.tour.TourDto;
import com.example.capstone_be.repository.ImageRepository;
import com.example.capstone_be.response.TourRespone;
import com.example.capstone_be.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    private final ImageRepository imageRepository;

    public ImageController(ImageService imageService, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }
    @PostMapping("/create")
    public ResponseEntity<List<ImageDto>> createImageDetailForTour(@RequestBody List<ImageDto> imageDtos) {
        imageService.createImageDetailForTour(imageDtos);
        return new ResponseEntity<>(imageDtos, HttpStatus.CREATED);
    }
}
