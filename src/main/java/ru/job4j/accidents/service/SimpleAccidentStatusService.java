package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentStatus;

import ru.job4j.accidents.repository.AccidentStatusDataRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SimpleAccidentStatusService implements AccidentStatusService {

    private final AccidentStatusDataRepository statuses;

    public SimpleAccidentStatusService(AccidentStatusDataRepository statuses) {
        this.statuses = statuses;
    }


    public List<AccidentStatus> getStatuses() {
        return (List<AccidentStatus>) statuses.findAll();
    }

    public AccidentStatus findById(int id) {
        return statuses.findById(id).orElseThrow(() ->
                new NoSuchElementException("Статус нарушения с " + id + " не найден"));
    }
}