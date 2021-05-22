package com.voronina.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CarDto {

    @Getter
    @Setter
    private PointDto positionDto;

    @Getter
    @Setter
    private int speed;
}
