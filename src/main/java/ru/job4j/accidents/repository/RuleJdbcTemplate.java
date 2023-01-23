package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate implements RuleRepository {

    private final JdbcTemplate jdbc;

    private static final String SELECT_RULE = "select * from rule";
    private static final String SELECT_RULE_ID = "select * from rule where id = ?";


    @Override
    public List<Rule> getRules() {
        return jdbc.query(SELECT_RULE,
                        new BeanPropertyRowMapper<>(Rule.class)).stream()
                .toList();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject(SELECT_RULE_ID,
                new BeanPropertyRowMapper<>(Rule.class), id));
    }
}
