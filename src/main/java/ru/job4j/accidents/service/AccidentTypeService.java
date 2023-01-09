package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeMem;

import java.util.List;

@Service
@AllArgsConstructor
public class AccidentTypeService {

    private final AccidentTypeMem types;

    public List<AccidentType> getTypes() {
        return types.getTypes();
    }

    public AccidentType findById(int id) {
        return types.findById(id).orElse(null);
    }
}
