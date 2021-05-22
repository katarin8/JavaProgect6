package com.voronina.services.implementations;

import com.voronina.data.dto.RoadDto;
import com.voronina.services.ICrudService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("roadService")
public class RoadService implements ICrudService<RoadDto> {

    @Override
    public RoadDto read(Integer id) {
        return null;
    }

    @Override
    public List<RoadDto> readAll() {
        return null;
    }

    @Override
    public void create(RoadDto roadDto) {

    }

    @Override
    public void update(RoadDto roadDto, RoadDto newT) {

    }

    @Override
    public void delete(RoadDto roadDto) {

    }
}
