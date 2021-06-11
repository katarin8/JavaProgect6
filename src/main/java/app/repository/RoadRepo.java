package app.repository;

import app.domain.model.Road;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoadRepo {
    Optional<Road> get(Long id);

    List<Road> getAll();

    void save(Road road);

    void update(Road road);

    void delete(Long id);

    void delete(Road road);

    void clear();
}
