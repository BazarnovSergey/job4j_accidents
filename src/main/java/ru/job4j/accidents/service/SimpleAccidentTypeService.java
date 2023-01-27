package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeDataRepository;

import java.util.List;

@Service
public class SimpleAccidentTypeService implements AccidentTypeService {

    private final AccidentTypeDataRepository types;

    public SimpleAccidentTypeService(AccidentTypeDataRepository types) {
        this.types = types;
    }

    public List<AccidentType> getTypes() {
        return (List<AccidentType>) types.findAll();
    }

    public AccidentType findById(int id) {
        return types.findById(id).orElse(null);
    }
}
