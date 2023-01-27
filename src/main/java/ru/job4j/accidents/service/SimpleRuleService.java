package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleDataRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SimpleRuleService implements RuleService {

    private final RuleDataRepository rules;

    public SimpleRuleService(RuleDataRepository rules) {
        this.rules = rules;
    }

    @Override
    public List<Rule> getRules() {
        return (List<Rule>) rules.findAll();
    }

    public Rule findById(int id) {
        return rules.findById(id).orElseThrow(() ->
                new NoSuchElementException("Статья нарушения не найдена"));
    }
}
