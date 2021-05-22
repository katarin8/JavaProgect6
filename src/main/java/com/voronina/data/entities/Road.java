package com.voronina.data.entities;

import com.voronina.data.physics.TwoDimensionsPhysicalObject;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

public class Road extends TwoDimensionsPhysicalObject {

    private Integer id;

    private TrafficLight trafficLight;

    private Road crossRoad;

    @OneToMany(
            mappedBy = "road",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Car> cars;

    public boolean canPlaceCar() {
        return true;
    }

    public void addCar(Car car) {
        this.cars.add(car);
    }

    public void removeCar(Car car) {
        this.cars.remove(car);
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    public Road getCrossRoad() {
        return crossRoad;
    }

    public void setCrossRoad(Road crossRoad) {
        this.crossRoad = crossRoad;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
