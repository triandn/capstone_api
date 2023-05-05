package com.example.capstone_be.controller;


import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.image.ImageViewDto;
import com.example.capstone_be.dto.tour.TourDto;
import com.example.capstone_be.repository.ImageRepository;
import com.example.capstone_be.response.TourRespone;
import com.example.capstone_be.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.example.capstone_be.util.ValidUtils.getMessageBindingResult;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    @PostMapping("/create/")
    public ResponseEntity<List<ImageDto>> createImageDetailForTour(@RequestBody List<ImageDto> imageDtos) {
        imageService.createImageDetailForTour(imageDtos);
        return new ResponseEntity<>(imageDtos, HttpStatus.CREATED);
    }

    @DeleteMapping("/image-delete/{id}")
    public ResponseEntity<ImageDto> deleteImage(@PathVariable UUID id) {
        imageService.deleteByImageId(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/image-update/{id}")
    public ResponseEntity<?> updateImage(@RequestBody @Valid ImageDto imageDto, @PathVariable UUID id, final BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String msg = getMessageBindingResult(bindingResult);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        ImageDto updatedImageDto = imageService.updateByImageId(imageDto, id);
        return new ResponseEntity(updatedImageDto, HttpStatus.OK);
    }

    @GetMapping("/all/")
    public ResponseEntity<List<ImageViewDto>> getAllImage() {
        final List<ImageViewDto> imageViewDtoList = imageService.getAllImage();
        return new ResponseEntity<>(imageViewDtoList,HttpStatus.OK);
    }
}
