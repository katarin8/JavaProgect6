package com.voronina.controllers.implementations;

import com.voronina.controllers.ICrudController;
import com.voronina.data.dto.TrafficLightDto;
import com.voronina.services.implementations.TrafficLightService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        path = "/traffic-light"
)
@CrossOrigin("*")
public class TrafficLightController implements ICrudController<TrafficLightDto> {

    @Qualifier("trafficLightService")
    private final TrafficLightService trafficLightService;

    public TrafficLightController(TrafficLightService trafficLightService) {
        this.trafficLightService = trafficLightService;
    }

    @Override
    @GetMapping(
            path = "/{id}"
    )
    public TrafficLightDto read(@PathVariable Integer id) {
        return null;
    }

    @Override
    public List<TrafficLightDto> readAll() {
        return null;
    }

    @Override
    public void create(TrafficLightDto trafficLightDto) {

    }

    @Override
    public void update(TrafficLightDto trafficLightDto, TrafficLightDto newT) {

    }

    @Override
    public void delete(TrafficLightDto trafficLightDto) {

    }
}
