package ru.job4j.accidents.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;

@Service
public class SimpleAccidentTypeService implements AccidentTypeService {

    private final AccidentTypeRepository types;

    public SimpleAccidentTypeService(
            @Qualifier("accidentTypeJdbcTemplate") AccidentTypeRepository types) {
        this.types = types;
    }

    public List<AccidentType> getTypes() {
        return types.getTypes();
    }

    public AccidentType findById(int id) {
        return types.findById(id).orElse(null);
    }
}
