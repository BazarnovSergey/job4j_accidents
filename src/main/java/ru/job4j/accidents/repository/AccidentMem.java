package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final AtomicInteger total = new AtomicInteger();

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    {
        Accident accident1 = new Accident(total.incrementAndGet(), "name1", "text1", "address1", new AccidentType());
        Accident accident2 = new Accident(total.incrementAndGet(), "name2", "text2", "address2", new AccidentType());
        Accident accident3 = new Accident(total.incrementAndGet(), "name3", "text3", "address3", new AccidentType());
        accidents.put(accident1.getId(), accident1);
        accidents.put(accident2.getId(), accident2);
        accidents.put(accident3.getId(), accident3);
    }

    public List<Accident> getAccidents() {
        return accidents.values().stream().toList();
    }

    public void createAccident(Accident accident) {
        accident.setId(total.incrementAndGet());
        accidents.put(accident.getId(), accident);
    }

    public void updateAccident(int id, Accident accident) {
        accidents.replace(id, accident);
    }

    public Optional<Accident> findById(int id) {
        return Optional.of(accidents.get(id));
    }
}
