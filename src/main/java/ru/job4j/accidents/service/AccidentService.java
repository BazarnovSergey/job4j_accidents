package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentMem;
import ru.job4j.accidents.repository.AccidentTypeMem;
import ru.job4j.accidents.repository.RuleMem;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentMem accidentMem;
    private final AccidentTypeMem types;
    private final RuleMem rules;

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

    /**
     * Метод принимает из формы массив строк с id статей нарушений,
     * находит их в базе и добавляет в инцидент
     *
     * @param ids      массив строк содержащий id статей нарушений
     * @param accident инцидент
     */
    public void addTypesToAccident(String[] ids, Accident accident) {
        Set<Rule> rulesList = new HashSet<>();
        Arrays.stream(ids).forEach(id -> rulesList.add(rules.findById(Integer.parseInt(id)).orElseThrow(() ->
                new NoSuchElementException("Ошибка добавления статьи в инцидент"))));
        accident.setRules(rulesList);
    }
}