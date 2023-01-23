package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RuleMem implements RuleRepository {

    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    {
        Rule rule1 = new Rule(1, "Статья. 1");
        Rule rule2 = new Rule(2, "Статья. 2");
        Rule rule3 = new Rule(3, "Статья. 3");
        rules.put(rule1.getId(), rule1);
        rules.put(rule2.getId(), rule2);
        rules.put(rule3.getId(), rule3);
    }

    public List<Rule> getRules() {
        return rules.values().stream().toList();
    }

    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

}
