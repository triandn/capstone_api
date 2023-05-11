package com.example.capstone_be.dto.image;

import com.example.capstone_be.model.Tour;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto implements Serializable {

    private UUID imageId = UUID.randomUUID();

    private String link;

    private Long tourId;

}
