package app.repository.impl;

import app.domain.model.Line;
import app.repository.LineRepository;
import app.repository.RoadBlockRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LineRepositoryImpl implements LineRepository {

    private final SessionFactory sessionFactory;
    private final RoadBlockRepository roadBlockRepository;

    @Autowired
    public LineRepositoryImpl(SessionFactory sessionFactory, RoadBlockRepository roadBlockRepository) {
        this.sessionFactory = sessionFactory;
        this.roadBlockRepository = roadBlockRepository;
    }


    @Override
    public Optional<Line> get(Long id) {
        Session session = sessionFactory.openSession();
        var result = session.get(Line.class, id);
        if (result == null) {
            var c = 0;
        }

        result.getBlockList().size();

        for (int i = 0; i < result.getLineLength(); i++) {
            result.getBlockList().set(i, roadBlockRepository.get(result.getBlockList().get(i).getId()).get());
        }
        session.close();
        return Optional.of(result);
    }


    @Override
    public List<Line> getAll() {
        Session session = sessionFactory.openSession();
        var query = session.createQuery("from lines", Line.class);
        var result = query.getResultList();

        result.forEach(line -> {
            line.getBlockList().size();

            for (int i = 0; i < line.getLineLength(); i++) {
                line.getBlockList().set(i, roadBlockRepository.get(line.getBlockList().get(i).getId()).get());
            }
        });

        session.close();
        return result;
    }

    @Override
    public void save(Line line) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(line);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Line line) {
        var current = get(line.getId());
        Session session = sessionFactory.openSession();
        var trans = session.beginTransaction();
        //session.evict(current);
        session.update(line);
        trans.commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        var session = sessionFactory.openSession();
        var trans = session.beginTransaction();
        var curr = session.get(Line.class, id);
        session.delete(curr);
        trans.commit();
        session.close();
    }

    @Override
    public void delete(Line line) {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        var curr = session.get(Line.class, line.getId());
        session.delete(curr);
        transaction.commit();
        session.close();
    }

    @Override
    public void clear() {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.createQuery("DELETE from lines").executeUpdate();
            transaction.commit();
            session.close();
        } catch (SQLGrammarException ignored) {
        }
    }
}
