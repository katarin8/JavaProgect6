package com.voronina.repositories;

import com.voronina.data.entities.Crossroad;
import org.springframework.data.repository.CrudRepository;

public interface TrafficLightRepository extends CrudRepository<Crossroad, Integer> {
}
