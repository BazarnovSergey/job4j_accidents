package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.AccidentStatus;

import java.util.List;
import java.util.Optional;

public interface AccidentStatusRepository {

    List<AccidentStatus> getStatuses();

    Optional<AccidentStatus> findById(int id);
}
