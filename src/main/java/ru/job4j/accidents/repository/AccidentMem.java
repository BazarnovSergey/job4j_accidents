package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem {

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    {
        Accident accident1 = new Accident(1, "name1", "text1", "address1");
        Accident accident2 = new Accident(2, "name2", "text2", "address2");
        Accident accident3 = new Accident(3, "name3", "text3", "address3");
        accidents.put(accident1.getId(), accident1);
        accidents.put(accident2.getId(), accident2);
        accidents.put(accident3.getId(), accident3);
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }
}
