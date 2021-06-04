package app.repository;

import app.domain.model.Line;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineRepo {
    Optional<Line> get(Long id);

    List<Line> getAll();

    void save(Line line);

    void update(Line line);

    void delete(Long id);

    void delete(Line line);

    void clear();
}
