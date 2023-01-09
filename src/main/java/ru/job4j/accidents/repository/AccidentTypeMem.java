package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AccidentTypeMem {

    private final List<AccidentType> types = new ArrayList<>();

    {
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));
    }

    public List<AccidentType> getTypes() {
        return types;
    }

    public Optional<AccidentType> findById(int id) {
        for (AccidentType type : types) {
            if (type.getId() == id) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}
