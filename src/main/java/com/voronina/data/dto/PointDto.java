package com.voronina.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class PointDto {

    @Getter
    @Setter
    private int x;

    @Getter
    @Setter
    private int y;
}
