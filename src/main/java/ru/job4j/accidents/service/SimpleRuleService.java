package ru.job4j.accidents.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SimpleRuleService implements RuleService {

    private final RuleRepository rules;

    public SimpleRuleService(@Qualifier("ruleJdbcTemplate") RuleRepository rules) {
        this.rules = rules;
    }

    public List<Rule> getRules() {
        return rules.getRules();
    }

    public Rule findById(int id) {
        return rules.findById(id).orElseThrow(() ->
                new NoSuchElementException("Статья нарушения не найдена"));
    }
}
