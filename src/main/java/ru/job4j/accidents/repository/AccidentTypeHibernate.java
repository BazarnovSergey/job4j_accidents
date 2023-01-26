package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHibernate implements AccidentTypeRepository {

    private final SessionFactory sf;

    @Override
    public List<AccidentType> getTypes() {
        List<AccidentType> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            try {
                result = session.createQuery("select at from AccidentType at", AccidentType.class).getResultList();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
        return result;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        Optional<AccidentType> result = Optional.empty();
        try (Session session = sf.openSession()) {
            try {
                session.beginTransaction();
                session.get(AccidentType.class, id);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
        return result;
    }
}