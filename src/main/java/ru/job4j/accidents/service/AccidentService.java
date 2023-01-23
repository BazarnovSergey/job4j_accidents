package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.List;

public interface AccidentService {

    List<Accident> getAccidents();

    Accident findById(int id);

    void createAccident(Accident accident, String[] ids, byte[] photo);

    void updateAccident(int id, Accident accident, String[] ids, byte[] photo);
}
