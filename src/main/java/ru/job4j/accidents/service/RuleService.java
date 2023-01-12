package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleMem;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleMem rules;

    public List<Rule> getRules() {
        return rules.getRules();
    }

    public Rule findById(int id) {
        return rules.findById(id).orElseThrow(() ->
                new NoSuchElementException("Статья нарушения не найдена"));
    }
}
