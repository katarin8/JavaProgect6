package app.repository.impl;

import app.domain.model.Road;
import app.repository.RoadRepo;
import app.repository.RoadBlockRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoadRepoImpl implements RoadRepo {

    private final SessionFactory sessionFactory;
    private final RoadBlockRepo roadBlockRepo;

    @Autowired
    public RoadRepoImpl(SessionFactory sessionFactory, RoadBlockRepo roadBlockRepo) {
        this.sessionFactory = sessionFactory;
        this.roadBlockRepo = roadBlockRepo;
    }


    @Override
    public Optional<Road> get(Long id) {
        Session session = sessionFactory.openSession();
        var result = session.get(Road.class, id);
        if (result == null) {
            var c = 0;
        }

        result.getBlockList().size();

        for (int i = 0; i < result.getLineLength(); i++) {
            result.getBlockList().set(i, roadBlockRepo.get(result.getBlockList().get(i).getId()).get());
        }
        session.close();
        return Optional.of(result);
    }


    @Override
    public List<Road> getAll() {
        Session session = sessionFactory.openSession();
        var query = session.createQuery("from lines", Road.class);
        var result = query.getResultList();

        result.forEach(road -> {
            road.getBlockList().size();

            for (int i = 0; i < road.getLineLength(); i++) {
                road.getBlockList().set(i, roadBlockRepo.get(road.getBlockList().get(i).getId()).get());
            }
        });

        session.close();
        return result;
    }

    @Override
    public void save(Road road) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(road);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Road road) {
        var current = get(road.getId());
        Session session = sessionFactory.openSession();
        var trans = session.beginTransaction();
        //session.evict(current);
        session.update(road);
        trans.commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        var session = sessionFactory.openSession();
        var trans = session.beginTransaction();
        var curr = session.get(Road.class, id);
        session.delete(curr);
        trans.commit();
        session.close();
    }

    @Override
    public void delete(Road road) {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        var curr = session.get(Road.class, road.getId());
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
