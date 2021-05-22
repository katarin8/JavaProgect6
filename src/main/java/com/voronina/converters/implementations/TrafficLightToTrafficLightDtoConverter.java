package com.voronina.converters.implementations;

import com.voronina.converters.BaseObjectConverter;
import com.voronina.data.dto.TrafficLightDto;
import com.voronina.data.entities.TrafficLight;
import org.springframework.stereotype.Component;

@Component
public class TrafficLightToTrafficLightDtoConverter extends BaseObjectConverter<TrafficLight, TrafficLightDto> {

    @Override
    public TrafficLightDto convert(TrafficLight obj) {
        //TODO: complete traffic light to traffic light dto converter
        return null;
    }

    @Override
    public Class<TrafficLight> getInClass() {
        return null;
    }

    @Override
    public Class<TrafficLightDto> getOutClass() {
        return null;
    }
}
