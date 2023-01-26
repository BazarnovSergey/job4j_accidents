package ru.job4j.accidents.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.*;

import java.util.*;

@Service
public class SimpleAccidentService implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository types;
    private final AccidentStatusRepository statuses;
    private final RuleRepository rules;

    public SimpleAccidentService(@Qualifier("accidentHibernate") AccidentRepository accidentRepository,
                                 @Qualifier("accidentTypeHibernate") AccidentTypeRepository types,
                                 @Qualifier("accidentStatusHibernate") AccidentStatusRepository statuses,
                                 @Qualifier("ruleHibernate") RuleRepository rules) {
        this.accidentRepository = accidentRepository;
        this.types = types;
        this.statuses = statuses;
        this.rules = rules;
    }

    public List<Accident> getAccidents() {
        return accidentRepository.getAccidents();
    }

    /**
     * Метод добавляет нарушение в базу
     *
     * @param accident - нарушение
     * @param ruleIds  - массив содержащий id статей нарушений
     * @param photo    - фото нарушения
     */
    public void createAccident(Accident accident, String[] ruleIds, byte[] photo) {
        fillsFieldsAccident(accident, ruleIds, photo);
        accidentRepository.createAccident(accident);
    }

    /**
     * Метод заменяет существующее нарушение на новое
     *
     * @param id       - id нарушения
     * @param accident - нарушение
     * @param ruleIds  - массив содердащий id статей нарушений
     * @param photo    - фото нарушения
     */
    public void updateAccident(int id, Accident accident, String[] ruleIds, byte[] photo) {
        fillsFieldsAccident(accident, ruleIds, photo);
        accidentRepository.updateAccident(id, accident);
    }

    /**
     * Метод находит нарушение по id
     *
     * @param id - id инцидента
     * @return возвращает найденный инцедент
     */
    public Accident findById(int id) {
        return accidentRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Инцидент с id: " + id + " не существует"));
    }

    /**
     * Метод добавляет пришедшие из формы параметры полей в объект типа Accident
     *
     * @param accident нарушение
     * @param ruleIds  массив строк с id статей нарушений
     * @param photo    - фото нарушения
     */
    private void fillsFieldsAccident(Accident accident, String[] ruleIds, byte[] photo) {
        Set<Rule> rulesList = new HashSet<>();
        accident.setPhoto(photo);
        Arrays.stream(ruleIds).forEach(id -> rulesList.add(rules.findById(Integer.parseInt(id)).orElseThrow(() ->
                new NoSuchElementException("Ошибка добавления статьи в инцидент"))));
        accident.setRules(rulesList);
        accident.setAccidentStatus(statuses.findById(accident.getAccidentStatus().getId()).orElseThrow(() ->
                new NoSuchElementException("Статус инцидента не найден")));
        accident.setType(types.findById(accident.getType().getId()).orElseThrow(() ->
                new NoSuchElementException("Тип инцидента не найден")));
    }

}