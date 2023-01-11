package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;
import ru.job4j.accidents.repository.AccidentTypeMem;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentMem accidentMem;
    private final AccidentTypeMem types;

    public List<Accident> getAccidents() {
        return accidentMem.getAccidents();
    }

    public void createAccident(Accident accident) {
        accident.setType(types.findById(accident.getType().getId()).orElseThrow(() ->
                new NoSuchElementException("Тип инцидента не найден")));
        accidentMem.createAccident(accident);
    }

    public void updateAccident(int id, Accident accident) {
        accident.setType(types.findById(accident.getType().getId()).orElseThrow(() ->
                new NoSuchElementException("Тип инцидента не найден")));
        accidentMem.updateAccident(id, accident);
    }

    public Accident findById(int id) {
        Accident accident = new Accident();
        if (accidentMem.findById(id).isPresent()) {
            accident = accidentMem.findById(id).get();
        }
        return accident;
    }
}
