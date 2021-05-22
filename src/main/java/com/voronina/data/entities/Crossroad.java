package com.voronina.data.entities;

import com.voronina.data.physics.TwoDimensionsPhysicalObject;

public class Crossroad extends TwoDimensionsPhysicalObject {

    private Integer id;

    private Road mainRoad;

    private Road crossRoad;

    public Road getMainRoad() {
        return mainRoad;
    }

    public void setMainRoad(Road mainRoad) {
        this.mainRoad = mainRoad;
    }

    public Road getCrossRoad() {
        return crossRoad;
    }

    public void setCrossRoad(Road crossRoad) {
        this.crossRoad = crossRoad;
    }
}
