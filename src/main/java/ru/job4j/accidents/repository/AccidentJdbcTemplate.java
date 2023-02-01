package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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

@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

    private final JdbcTemplate jdbc;

    private final static String INSERT_ACCIDENT = "insert into accident (name,description_accident,car_number, type_id,"
            + "address_accident,status_id,photo,date_accident) values (?,?,?,?,?,?,?,?)";

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
        String sql = "select distinct ac.id, ac.name, "
                + "ac.description_accident, "
                + "ac.car_number ,"
                + "ac.type_id, "
                + "ac.address_accident, "
                + "ac.status_id, "
                + "ac.photo, "
                + "ac.date_accident, "
                + "at.id as type_id, "
                + "at.name as type_name, "
                + "ast.id as status_id, "
                + "ast.status, "
                + "r.id as rule_id, "
                + "r.name as rule_name "
                + "from accident ac "
                + "join accident_type at on at.id = ac.type_id "
                + "join accident_rule ar on ac.id = ar.accident_id "
                + "join accident_status ast on ast.id = ac.status_id "
                + "join rule r on r.id = ar.rule_id "
                + "where ac.id = ?";
        Map<Integer, Accident> accidentById = jdbc.query(sql, extractAccident, id);
        return Optional.ofNullable(accidentById.get(id));
    }

    @Override
    public List<Accident> getAccidents() {
        String sql = "select distinct ac.id, ac.name, "
                + "ac.description_accident, "
                + "ac.car_number ,"
                + "ac.type_id, "
                + "ac.address_accident, "
                + "ac.status_id, "
                + "ac.photo, "
                + "ac.date_accident, "
                + "at.id as type_id, "
                + "at.name as type_name, "
                + "ast.id as status_id, "
                + "ast.status, "
                + "r.id as rule_id, "
                + "r.name as rule_name "
                + "from accident ac "
                + "join accident_type at on at.id = ac.type_id "
                + "join accident_rule ar on ac.id = ar.accident_id "
                + "join accident_status ast on ast.id = ac.status_id "
                + "join rule r on r.id = ar.rule_id";
        Map<Integer, Accident> accidents = jdbc.query(sql, extractAccident);
        assert accidents != null;
        return new ArrayList<>(accidents.values());
    }

    private final ResultSetExtractor<Map<Integer, Accident>> extractAccident = (rs) -> {
        Map<Integer, Accident> data = new HashMap<>();
        while (rs.next()) {
            Accident accident = new Accident();
            accident.setId(rs.getInt("id"));
            accident.setName(rs.getString("name"));
            accident.setText(rs.getString("description_accident"));
            accident.setCarNumber(rs.getString("car_number"));
            AccidentType type = new AccidentType();
            type.setId(rs.getInt("type_id"));
            type.setName(rs.getString("type_name"));
            accident.setType(type);
            accident.setAddress(rs.getString("address_accident"));
            AccidentStatus status = new AccidentStatus();
            status.setId(rs.getInt("status_id"));
            status.setStatus(rs.getString("status"));
            accident.setAccidentStatus(status);
            accident.setPhoto(rs.getBytes("photo"));
            accident.setDateAccident(rs.getTimestamp("date_accident").toLocalDateTime());
            accident.setRules(new HashSet<>(Set.of(new Rule(rs.getInt("rule_id"), rs.getString("rule_name")))));
            data.putIfAbsent(accident.getId(), accident);
        }
        return data;
    };
}