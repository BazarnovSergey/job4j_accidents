package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RuleHibernate implements RuleRepository {

    private final SessionFactory sf;

    @Override
    public List<Rule> getRules() {
        List<Rule> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            try {
                result = session.createQuery("select r from Rule r", Rule.class).getResultList();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
        return result;
    }

    @Override
    public Optional<Rule> findById(int id) {
        Optional<Rule> result = Optional.empty();
        try (Session session = sf.openSession()) {
            try {
                session.beginTransaction();
                session.get(Rule.class, id);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
        return result;
    }
}
