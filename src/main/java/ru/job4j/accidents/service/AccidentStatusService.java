package ru.job4j.accidents.service;

import ru.job4j.accidents.model.AccidentStatus;

import java.util.List;

public interface AccidentStatusService {

    List<AccidentStatus> getStatuses();

    AccidentStatus findById(int id);

}
