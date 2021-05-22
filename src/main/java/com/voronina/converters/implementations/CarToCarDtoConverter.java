package com.voronina.converters.implementations;

import com.voronina.converters.BaseObjectConverter;
import com.voronina.data.dto.CarDto;
import com.voronina.data.entities.Car;
import org.springframework.stereotype.Component;

@Component
public class CarToCarDtoConverter extends BaseObjectConverter<Car, CarDto> {

    @Override
    public CarDto convert(Car obj) {
        //TODO: complete car to car dto converter
        return null;
    }

    @Override
    public Class<Car> getInClass() {
        return Car.class;
    }

    @Override
    public Class<CarDto> getOutClass() {
        return CarDto.class;
    }
}
