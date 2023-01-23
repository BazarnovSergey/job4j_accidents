package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeJdbcTemplate implements AccidentTypeRepository {

    private final JdbcTemplate jdbc;

    @Override
    public List<AccidentType> getTypes() {
        return jdbc.query(
                        "select * from accident_type",
                        new BeanPropertyRowMapper<>(AccidentType.class)).stream()
                .toList();

    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject("select * from accident_type where id = ?",
                new BeanPropertyRowMapper<>(AccidentType.class), id));
    }
}
