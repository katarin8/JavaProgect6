package app.repository.impl;

import app.domain.model.Cars;
import app.repository.CarsRepo;
import app.repository.RoadBlockRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CarsRepoImpl implements CarsRepo {

    private final SessionFactory sessionFactory;

    private final RoadBlockRepo roadBlockRepo;


    @Autowired
    public CarsRepoImpl(SessionFactory sessionFactory, RoadBlockRepo roadBlockRepo) {
        this.sessionFactory = sessionFactory;
        this.roadBlockRepo = roadBlockRepo;
    }

    @Override
    public Optional<Cars> get(Long id) {
        Session session = sessionFactory.openSession();
        var result = session.get(Cars.class, id);

        if (result.getRoadBlock() instanceof HibernateProxy) {
            var proxy = (HibernateProxy) result.getRoadBlock();
            if (proxy != null) {
                proxy.getHibernateLazyInitializer().getImplementation();
            }
        }

        session.close();
        return Optional.of(result);
    }

    @Override
    public List<Cars> getAll() {
        Session session = sessionFactory.openSession();
        var query = session.createQuery("from cars", Cars.class);
        List<Cars> result = query.getResultList();

        result.forEach(res -> {
            if (res.getRoadBlock() != null)
                res.setRoadBlock(roadBlockRepo.get(res.getRoadBlock().getId()).get());
        });

        session.close();
        return result;
    }

    @Override
    public void save(Cars entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Cars entity) {
        var current = get(entity.getId());
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
        var curr = session.get(Cars.class, id);

        curr.getRoadBlock().setCars(null);
        trans.commit();
        session.close();
        roadBlockRepo.update(curr.getRoadBlock());

        session = sessionFactory.openSession();
        trans = session.beginTransaction();
        //curr.setRoadBlock(null);

        session.delete(curr);
        trans.commit();
        session.close();
    }

    @Override
    public void delete(Cars entity) {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        var curr = session.get(Cars.class, entity.getId());
        session.delete(curr);
        transaction.commit();
        session.close();
    }

    @Override
    public void clear() {
        try (var session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from cars ").executeUpdate();
            transaction.commit();
        } catch (Exception ignored) {
        }
    }
}
