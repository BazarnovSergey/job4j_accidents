package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentStatus;

public interface AccidentStatusDataRepository extends CrudRepository<AccidentStatus, Integer> {
}
