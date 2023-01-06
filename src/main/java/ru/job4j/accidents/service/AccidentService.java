package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentMem accidentMem;

    public List<Accident> getAccidents() {
        return accidentMem.getAccidents();
    }

    public void createAccident(Accident accident) {
        accidentMem.createAccident(accident);
    }

    public void updateAccident(int id, Accident accident) {
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
