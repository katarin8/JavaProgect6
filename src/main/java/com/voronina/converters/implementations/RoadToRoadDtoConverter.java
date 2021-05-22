package com.voronina.converters.implementations;

import com.voronina.converters.BaseObjectConverter;
import com.voronina.data.dto.RoadDto;
import com.voronina.data.entities.Road;
import org.springframework.stereotype.Component;

@Component
public class RoadToRoadDtoConverter extends BaseObjectConverter<Road, RoadDto> {

    @Override
    public RoadDto convert(Road obj) {
        //TODO: complete road to road dto converter
        return null;
    }

    @Override
    public Class<Road> getInClass() {
        return Road.class;
    }

    @Override
    public Class<RoadDto> getOutClass() {
        return RoadDto.class;
    }
}
