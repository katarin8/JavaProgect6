package com.voronina.controllers.implementations;

import com.voronina.controllers.ICrudController;
import com.voronina.data.dto.CarDto;
import com.voronina.services.ICrudService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        path = "/car"
)
@CrossOrigin("*")
public class CarController implements ICrudController<CarDto> {

    @Qualifier("carService")
    private final ICrudService<CarDto> carService;

    public CarController(ICrudService<CarDto> carService) {
        this.carService = carService;
    }

    @Override
    @GetMapping(
            path = "/{id}"
    )
    public CarDto read(@PathVariable Integer id) {
        return null;
    }

    @Override
    public List<CarDto> readAll() {
        return null;
    }

    @Override
    public void create(CarDto carDto) {

    }

    @Override
    public void update(CarDto carDto, CarDto newT) {

    }

    @Override
    public void delete(CarDto carDto) {

    }
}
