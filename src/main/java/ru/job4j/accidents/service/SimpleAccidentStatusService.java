package ru.job4j.accidents.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentStatus;

import ru.job4j.accidents.repository.AccidentStatusRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SimpleAccidentStatusService implements AccidentStatusService {

    private final AccidentStatusRepository statuses;

    public SimpleAccidentStatusService(@Qualifier("accidentStatusHibernate") AccidentStatusRepository statuses) {
        this.statuses = statuses;
    }

    public List<AccidentStatus> getStatuses() {
        return statuses.getStatuses();
    }

    public AccidentStatus findById(int id) {
        return statuses.findById(id).orElseThrow(() ->
                new NoSuchElementException("Статус нарушения с " + id + " не найден"));
    }
}