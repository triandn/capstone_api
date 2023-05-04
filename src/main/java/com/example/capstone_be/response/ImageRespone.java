package com.example.capstone_be.response;

import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.tour.TourViewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRespone {
    private List<ImageDto> data;
}