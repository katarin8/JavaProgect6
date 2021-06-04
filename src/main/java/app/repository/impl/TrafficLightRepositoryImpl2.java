package app.repository.impl;


import app.domain.model.Automobile;
import app.domain.model.TrafficLight;
import app.repository.RoadBlockRepository;
import app.repository.TrafficLightRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TrafficLightRepositoryImpl2 implements TrafficLightRepository {
    private final SessionFactory sessionFactory;
    private final RoadBlockRepository roadBlockRepository;

    @Autowired
    public TrafficLightRepositoryImpl2(SessionFactory sessionFactory, RoadBlockRepository roadBlockRepository) {
        this.sessionFactory = sessionFactory;
        this.roadBlockRepository = roadBlockRepository;
    }



    @Override
    public Optional<TrafficLight> get(Long id) {
        Session session = sessionFactory.openSession();
        var result = session.get(TrafficLight.class, id);

        result.getControlledBlocks().size();
        session.close();

        for (int i = 0; i < result.getControlledBlocks().size(); i++) {
            result.getControlledBlocks().set(i, roadBlockRepository.get(result.getControlledBlocks().get(i).getId()).get());
        }

        return Optional.of(result);
    }

    @Override
    public List<TrafficLight> getAll() {
        Session session = sessionFactory.openSession();
        var query = session.createQuery("from trafficLight ", TrafficLight.class);
        var result = query.getResultList();

        result.forEach(res -> {
            res.getControlledBlocks().size();
        });

        session.close();

        result.forEach(res -> {
            for (int i = 0; i < res.getControlledBlocks().size(); i++) {
                res.getControlledBlocks().set(i, roadBlockRepository.get(res.getControlledBlocks().get(i).getId()).get());
            }
        });

        return result;
    }

    @Override
    public void save(TrafficLight entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(TrafficLight entity) {
        Session session = sessionFactory.openSession();
        var trans = session.beginTransaction();
        session.update(entity);
        trans.commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        var session = sessionFactory.openSession();
        var trans = session.beginTransaction();
        var curr = session.get(Automobile.class, id);
        session.delete(curr);
        trans.commit();
        session.close();
    }

    @Override
    public void delete(TrafficLight entity) {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        var curr = session.get(TrafficLight.class, entity.getId());
        session.delete(curr);
        transaction.commit();
        session.close();
    }

    @Override
    public void clear() {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.createQuery("delete from trafficLight ").executeUpdate();
            transaction.commit();
        } catch (Exception ignored) {
        }
    }
}
