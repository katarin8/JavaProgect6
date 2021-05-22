package com.voronina.converters.implementations;

import com.voronina.converters.BaseObjectConverter;
import com.voronina.data.dto.CrossroadDto;
import com.voronina.data.entities.Crossroad;
import org.springframework.stereotype.Component;

@Component
public class CrossroadToCrossroadDtoConverter extends BaseObjectConverter<Crossroad, CrossroadDto> {

    @Override
    public CrossroadDto convert(Crossroad obj) {
        //TODO: complete crossroad to crossroad dto converter
        return null;
    }

    @Override
    public Class<Crossroad> getInClass() {
        return Crossroad.class;
    }

    @Override
    public Class<CrossroadDto> getOutClass() {
        return CrossroadDto.class;
    }
}
