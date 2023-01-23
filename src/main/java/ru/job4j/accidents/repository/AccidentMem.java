package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentStatus;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements AccidentRepository {

    private final AtomicInteger total = new AtomicInteger();

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    {
        Accident accident1 = Accident.builder()
                .id(total.incrementAndGet())
                .name("name1")
                .text("tex1")
                .carNumber("o111oo11")
                .type(new AccidentType())
                .rules(Set.of(new Rule()))
                .address("address")
                .accidentStatus(new AccidentStatus())
                .dateAccident(LocalDateTime.now())
                .build();

        Accident accident2 = Accident.builder()
                .id(total.incrementAndGet())
                .name("name2")
                .text("tex2")
                .carNumber("o222oo22")
                .type(new AccidentType())
                .rules(Set.of(new Rule()))
                .address("address")
                .accidentStatus(new AccidentStatus())
                .dateAccident(LocalDateTime.now())
                .build();

        Accident accident3 = Accident.builder()
                .id(total.incrementAndGet())
                .name("name3")
                .text("tex3")
                .carNumber("o333oo33")
                .type(new AccidentType())
                .rules(Set.of(new Rule()))
                .address("address")
                .accidentStatus(new AccidentStatus())
                .dateAccident(LocalDateTime.now())
                .build();

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
