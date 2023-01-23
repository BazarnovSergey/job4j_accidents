package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentStatus;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

    private final JdbcTemplate jdbc;

    private final static String INSERT_ACCIDENT = "insert into accident (name,description_accident,car_number, type_id,"
            + "address_accident,status_id,photo,date_accident) values (?,?,?,?,?,?,?,?)";

    private final static String SELECT_ACCIDENT = "select * from accident";

    private final static String SELECT_ACCIDENT_ID = "select * from accident where id = ?";

    private final static String SELECT_ACCIDENT_TYPE_ID = "select at.id, at.name from accident_type at "
            + "join accident a on at.id = a.type_id where a.id = ?";

    private final static String SELECT_RULE_ID = "SELECT * FROM rule WHERE id = ?";

    private final static String SELECT_ACCIDENT_STATUS_JOIN_ACCIDENT = "select ast.id, ast.status from"
            + " accident_status as ast " + " join accident a on ast.id = a.status_id where a.id = ?";

    private final static String SELECT_RULE_JOIN_ACCIDENT_RULE = "select * from rule as r join accident_rule ar "
            + "on r.id = ar.rule_id where accident_id = ?;";

    private final static String UPDATE_ACCIDENT = "update accident set name = ?,description_accident = ?,car_number = ?,"
            + " type_id = ?, address_accident = ?,status_id = ?,photo = ?,date_accident = ? where id = ?";

    public void createAccident(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(INSERT_ACCIDENT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, accident.getName());
            preparedStatement.setString(2, accident.getText());
            preparedStatement.setString(3, accident.getCarNumber());
            preparedStatement.setInt(4, accident.getType().getId());
            preparedStatement.setString(5, accident.getAddress());
            preparedStatement.setInt(6, accident.getAccidentStatus().getId());
            preparedStatement.setBytes(7, accident.getPhoto());
            preparedStatement.setTimestamp(8, Timestamp.valueOf(accident.getDateAccident()));
            return preparedStatement;
        }, keyHolder);
        int accidentId = (Integer) keyHolder.getKeys().get("id");
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rule (accident_id, rule_id) values (?,?)",
                    accidentId, rule.getId());
        }
    }

    @Override
    public void updateAccident(int id, Accident accident) {
        jdbc.update(UPDATE_ACCIDENT,
                accident.getName(),
                accident.getText(),
                accident.getCarNumber(),
                accident.getType().getId(),
                accident.getAddress(),
                accident.getAccidentStatus().getId(),
                accident.getPhoto(),
                accident.getDateAccident(),
                id);
        jdbc.update("delete from accident_rule  where accident_id = ?", id);
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rule (accident_id, rule_id) values (?,?)",
                    id, rule.getId());
        }
    }


    @Override
    public Optional<Accident> findById(int id) {
        return jdbc.queryForObject(SELECT_ACCIDENT_ID,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("description_accident"));
                    accident.setCarNumber(rs.getString("car_number"));
                    accident.setType(jdbc.query(SELECT_ACCIDENT_TYPE_ID,
                                    new Object[]{rs.getInt("id")},
                                    new BeanPropertyRowMapper<>(AccidentType.class))
                            .stream().findAny().orElse(null));
                    accident.setRules(new HashSet<>(jdbc.query(SELECT_RULE_JOIN_ACCIDENT_RULE,
                            new Object[]{rs.getInt("id")},
                            new BeanPropertyRowMapper<>(Rule.class))));
                    accident.setAddress(rs.getString("address_accident"));
                    accident.setAccidentStatus(jdbc.query(SELECT_ACCIDENT_STATUS_JOIN_ACCIDENT,
                                    new Object[]{rs.getInt("id")},
                                    new BeanPropertyRowMapper<>(AccidentStatus.class))
                            .stream().findAny().orElse(null));
                    accident.setPhoto(rs.getBytes("photo"));
                    accident.setDateAccident(rs.getTimestamp("date_accident").toLocalDateTime());
                    return Optional.of(accident);
                },
                id);
    }

    @Override
    public List<Accident> getAccidents() {
        return jdbc.query(SELECT_ACCIDENT,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("description_accident"));
                    accident.setCarNumber(rs.getString("car_number"));
                    accident.setType(jdbc.query(SELECT_ACCIDENT_TYPE_ID,
                                    new Object[]{rs.getInt("id")},
                                    new BeanPropertyRowMapper<>(AccidentType.class))
                            .stream().findAny().orElse(null));
                    accident.setRules(new HashSet<>(jdbc.query(SELECT_RULE_ID,
                            new Object[]{rs.getInt("id")},
                            new BeanPropertyRowMapper<>(Rule.class))));
                    accident.setAddress(rs.getString("address_accident"));
                    accident.setAccidentStatus(jdbc.query(SELECT_ACCIDENT_STATUS_JOIN_ACCIDENT,
                                    new Object[]{rs.getInt("id")},
                                    new BeanPropertyRowMapper<>(AccidentStatus.class))
                            .stream().findAny().orElse(null));
                    accident.setPhoto(rs.getBytes("photo"));
                    accident.setDateAccident(rs.getTimestamp("date_accident").toLocalDateTime());
                    return accident;
                });
    }
}
