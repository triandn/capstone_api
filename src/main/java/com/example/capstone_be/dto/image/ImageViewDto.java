package com.example.capstone_be.dto.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageViewDto implements Serializable {

    private UUID imageId;

    private String link;
}

