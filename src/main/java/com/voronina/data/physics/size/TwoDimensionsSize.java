package com.voronina.data.physics.size;


import lombok.*;

public class TwoDimensionsSize {


    @Setter
    @Getter
    private int width;

    @Setter
    @Getter
    private int height;

    public TwoDimensionsSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
