package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository {
    List<Accident> getAccidents();

    void createAccident(Accident accident);

    void updateAccident(int id, Accident accident);

    Optional<Accident> findById(int id);
}
