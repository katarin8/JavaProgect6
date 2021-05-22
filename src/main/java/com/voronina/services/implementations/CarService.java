package com.voronina.services.implementations;

import com.voronina.data.dto.CarDto;
import com.voronina.repositories.CarRepository;
import com.voronina.services.ICrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("carService")
public class CarService implements ICrudService<CarDto> {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarDto read(Integer id) {
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


