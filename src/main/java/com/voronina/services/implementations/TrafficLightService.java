package com.voronina.services.implementations;

import com.voronina.data.dto.TrafficLightDto;
import com.voronina.services.ICrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("trafficLightService")
public class TrafficLightService implements ICrudService<TrafficLightDto> {

    @Override
    public TrafficLightDto read(Integer id) {
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
