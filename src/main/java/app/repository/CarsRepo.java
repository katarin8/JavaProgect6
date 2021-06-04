package app.repository;

import app.domain.model.Cars;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarsRepo {
    Optional<Cars> get(Long id);

    List<Cars> getAll();

    void save(Cars entity);

    void update(Cars entity);

    void delete(Long id);

    void delete(Cars entity);

    void clear();
}
