package com.voronina.data.physics;

import com.voronina.data.physics.position.TwoDimensionsPoint;
import com.voronina.data.physics.size.TwoDimensionsSize;

public abstract class TwoDimensionsPhysicalObject extends PhysicalObject {

    protected TwoDimensionsSize size;

    protected TwoDimensionsPoint position;

    public TwoDimensionsSize getSize() {
        return size;
    }

    public void setSize(TwoDimensionsSize size) {
        this.size = size;
    }

    public TwoDimensionsPoint getPosition() {
        return position;
    }

    public void setPosition(TwoDimensionsPoint position) {
        this.position = position;
    }
}
