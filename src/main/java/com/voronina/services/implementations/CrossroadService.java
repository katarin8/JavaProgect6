package com.voronina.services.implementations;

import com.voronina.data.dto.CrossroadDto;
import com.voronina.services.ICrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("crossroadService")
public class CrossroadService implements ICrudService<CrossroadDto> {

    @Override
    public CrossroadDto read(Integer id) {
        return null;
    }

    @Override
    public List<CrossroadDto> readAll() {
        return null;
    }

    @Override
    public void create(CrossroadDto crossroadDto) {

    }

    @Override
    public void update(CrossroadDto crossroadDto, CrossroadDto newT) {

    }

    @Override
    public void delete(CrossroadDto crossroadDto) {

    }
}
