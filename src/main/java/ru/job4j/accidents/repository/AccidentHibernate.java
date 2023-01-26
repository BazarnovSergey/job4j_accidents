package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate implements AccidentRepository {


    private final SessionFactory sf;

    @Override
    public List<Accident> getAccidents() {
        List<Accident> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            try {
                result = session.createQuery("select a from Accident a join fetch a.type join fetch a.rules "
                        + "join fetch a.accidentStatus", Accident.class).getResultList();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
        return result;
    }

    @Override
    public void createAccident(Accident accident) {
        try (Session session = sf.openSession()) {
            try {
                session.save(accident);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void updateAccident(int id, Accident accident) {
        try (Session session = sf.openSession()) {
            try {
                session.update(accident);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public Optional<Accident> findById(int id) {
        Optional<Accident> result = Optional.empty();
        try (Session session = sf.openSession()) {
            try {
                session.beginTransaction();
                session.get(Accident.class, id);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
        return result;
    }
}
