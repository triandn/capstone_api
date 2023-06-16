package com.example.capstone_be.dto.tour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewPortSearchDto implements Serializable {
    private String northEastLat;
    private String northEastLng;
    private String southWestLat;
    private String southWestLng;
}
