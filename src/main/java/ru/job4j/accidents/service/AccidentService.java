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

    /**
     * Метод добавляет инцидент в базу
     *
     * @param accident - инцидент
     * @param ruleIds  - массив содердащий id статей нарушений
     */
    public void createAccident(Accident accident, String[] ruleIds) {
        addTypesAndRulesToAccident(accident, ruleIds);
        accidentMem.createAccident(accident);
    }

    /**
     * Метод заменяет существующий инцидент на новый
     *
     * @param id       - id инцидента
     * @param accident - инцидент
     * @param ruleIds  - массив содердащий id статей нарушений
     */
    public void updateAccident(int id, Accident accident, String[] ruleIds) {
        addTypesAndRulesToAccident(accident, ruleIds);
        accidentMem.updateAccident(id, accident);
    }

    /**
     * Метод находит инцедент по id
     *
     * @param id - id инцидента
     * @return возвращает найденный инцедент
     */
    public Accident findById(int id) {
        return accidentMem.findById(id).orElseThrow(() ->
                new NoSuchElementException("Инцидент с id: " + id + " не существует"));
    }

    /**
     * Метод добавляет в объект Accident статьи и тип нарушений
     *
     * @param accident инцидент
     * @param ruleIds  массив строк с id статей нарушений
     */
    private void addTypesAndRulesToAccident(Accident accident, String[] ruleIds) {
        Set<Rule> rulesList = new HashSet<>();
        Arrays.stream(ruleIds).forEach(id -> rulesList.add(rules.findById(Integer.parseInt(id)).orElseThrow(() ->
                new NoSuchElementException("Ошибка добавления статьи в инцидент"))));
        accident.setRules(rulesList);
        accident.setType(types.findById(accident.getType().getId()).orElseThrow(() ->
                new NoSuchElementException("Тип инцидента не найден")));
    }

}