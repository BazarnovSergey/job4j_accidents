package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentStatus;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentStatusJdbcTemplate implements AccidentStatusRepository {

    private final JdbcTemplate jdbc;

    private static final String SELECT_ACCIDENT_STATUS = "select * from accident_status";
    private static final String SELECT_ACCIDENT_STATUS_ID = "select * from accident_status where id = ?";

    /**
     * Метод получает из базы данных статусы заявки о нарушении
     * (Принята, отклонена, завершена)
     *
     * @return список со статусами заяки
     */
    @Override
    public List<AccidentStatus> getStatuses() {
        return jdbc.query(SELECT_ACCIDENT_STATUS,
                        new BeanPropertyRowMapper<>(AccidentStatus.class)).stream()
                .toList();
    }

    /**
     * Метод возвращает из базы данных заявку о нарушении с заданным id
     *
     * @param id - id статуса заявки
     * @return объект Optional содержащий статус заявки. Если объект не найден возвращает null
     */
    @Override
    public Optional<AccidentStatus> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject(SELECT_ACCIDENT_STATUS_ID,
                new BeanPropertyRowMapper<>(AccidentStatus.class), id));
    }
}
