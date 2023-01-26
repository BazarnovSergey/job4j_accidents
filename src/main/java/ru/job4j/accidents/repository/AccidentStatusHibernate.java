package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentStatusHibernate implements AccidentStatusRepository {

    private final SessionFactory sf;

    @Override
    public List<AccidentStatus> getStatuses() {
        List<AccidentStatus> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            try {
                result = session.createQuery("select ast from AccidentStatus ast",
                        AccidentStatus.class).getResultList();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
        return result;
    }

    @Override
    public Optional<AccidentStatus> findById(int id) {
        Optional<AccidentStatus> result = Optional.empty();
        try (Session session = sf.openSession()) {
            try {
                session.beginTransaction();
                session.get(AccidentStatus.class, id);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
        return result;
    }
}
