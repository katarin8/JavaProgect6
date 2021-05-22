package com.voronina.data.entities;

import com.voronina.data.states.TrafficLightMode;
import com.voronina.data.physics.TwoDimensionsPhysicalObject;
import com.voronina.data.states.TrafficLightState;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "traffic_light",
        schema = "public"
)
public class TrafficLight extends TwoDimensionsPhysicalObject {

    public TrafficLight() {

    }

    public TrafficLight(TrafficLightMode mode) {
        this.mode = mode;
    }

    @Id
    @SequenceGenerator(
            name = "traffic_light_sequence",
            sequenceName = "traffic_light_sequence",
            schema = "public",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "traffic_light_sequence"
    )
    @Column(
            name = "id"
    )
    private Integer id;

    @Column(
            name = "mode"
    )
    @Enumerated(
            EnumType.STRING
    )
    private TrafficLightMode mode;

    private TrafficLightState forwardDirectionState;
    private TrafficLightState leftTurnState;
    private TrafficLightState rightTurnState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TrafficLightMode getMode() {
        return mode;
    }

    public void setMode(TrafficLightMode mode) {
        this.mode = mode;
    }

    public TrafficLightState getForwardDirectionState() {
        return forwardDirectionState;
    }

    public void setForwardDirectionState(TrafficLightState forwardDirectionState) {
        this.forwardDirectionState = forwardDirectionState;
    }

    public TrafficLightState getLeftTurnState() {
        return leftTurnState;
    }

    public void setLeftTurnState(TrafficLightState leftTurnState) {
        this.leftTurnState = leftTurnState;
    }

    public TrafficLightState getRightTurnState() {
        return rightTurnState;
    }

    public void setRightTurnState(TrafficLightState rightTurnState) {
        this.rightTurnState = rightTurnState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrafficLight that = (TrafficLight) o;
        return Objects.equals(id, that.id) && mode == that.mode && forwardDirectionState == that.forwardDirectionState && leftTurnState == that.leftTurnState && rightTurnState == that.rightTurnState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mode, forwardDirectionState, leftTurnState, rightTurnState);
    }

    @Override
    public String toString() {
        return "TrafficLight{" +
                "id=" + id +
                ", mode=" + mode +
                ", forwardDirectionState=" + forwardDirectionState +
                ", leftTurnState=" + leftTurnState +
                ", rightTurnState=" + rightTurnState +
                '}';
    }
}
