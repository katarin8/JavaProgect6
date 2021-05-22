package com.voronina.controllers.implementations;

import com.voronina.controllers.ICrudController;
import com.voronina.data.dto.RoadDto;
import com.voronina.services.ICrudService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        path = "/road"
)
@CrossOrigin("*")
public class RoadController implements ICrudController<RoadDto> {

    @Qualifier("roadService")
    private final ICrudService<RoadDto> roadService;

    public RoadController(ICrudService<RoadDto> roadService) {
        this.roadService = roadService;
    }

    @Override
    @GetMapping(
            path = "/{id}"
    )
    public RoadDto read(@PathVariable Integer id) {
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
