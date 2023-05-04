package com.example.capstone_be.service;

import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.model.ImageDetail;
import com.example.capstone_be.repository.ImageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
}
