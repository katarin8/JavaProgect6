package com.voronina.data.entities;

import com.voronina.annotations.InjectRandomCarDirection;
import com.voronina.annotations.InjectRandomInt;
import com.voronina.data.physics.move.IMovableObject;
import com.voronina.data.physics.position.Point;
import com.voronina.data.physics.TwoDimensionsPhysicalObject;
import com.voronina.data.physics.size.TwoDimensionsSize;
import com.voronina.data.physics.position.TwoDimensionsPoint;
import com.voronina.data.states.CarDirection;
import javax.persistence.*;
import java.util.Objects;

@Entity(
        name = "Car"
)
@Table(
        name = "car",
        schema = "public"
)
public class Car extends TwoDimensionsPhysicalObject implements IMovableObject, Runnable {

    @Id
    @SequenceGenerator(
            name = "car_sequence",
            sequenceName = "car_sequence",
            schema = "public",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @InjectRandomInt(
            minValue = 30,
            maxValue = 120
    )
    @Column(
            name = "speed"
    )
    private Integer speed;

    @InjectRandomCarDirection
    @Enumerated(EnumType.STRING)
    @Column(
            name = "direction",
            columnDefinition = "VARCHAR",
            length = 7
    )
    private CarDirection direction;


    public Car() {
    }

    public Car(int width, int height, int startX, int startY, Road road) {
        this.size = new TwoDimensionsSize(width, height);
        this.position = new TwoDimensionsPoint(startX, startY);
    }

    @Override
    public void run() {

    }

    @Override
    public void move(Point newPosition) {
        //TODO: car movement
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public CarDirection getDirection() {
        return direction;
    }

    public void setDirection(CarDirection direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(this.position.getX(), this.position.getY())
                && Objects.equals(id, car.id)
                && Objects.equals(speed, car.speed)
                && direction == car.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, speed, direction);
    }


    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", speed=" + speed +
                ", direction=" + direction +
                ", position=" + position +
                ", size=" + size +
                '}';
    }
}
