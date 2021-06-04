package app.repository;

import app.domain.model.Automobile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutomobileRepository {
    Optional<Automobile> get(Long id);

    List<Automobile> getAll();

    void save(Automobile entity);

    void update(Automobile entity);

    void delete(Long id);

    void delete(Automobile entity);

    void clear();
}
