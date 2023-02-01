package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AccidentStatusMem implements AccidentStatusRepository {

    private final Map<Integer, AccidentStatus> statuses = new ConcurrentHashMap<>();

    {
        AccidentStatus accidentStatus1 = new AccidentStatus(1, "Принята");
        AccidentStatus accidentStatus2 = new AccidentStatus(2, "Отклонена");
        AccidentStatus accidentStatus3 = new AccidentStatus(3, "Завершена");
        statuses.put(accidentStatus1.getId(), accidentStatus1);
        statuses.put(accidentStatus2.getId(), accidentStatus2);
        statuses.put(accidentStatus3.getId(), accidentStatus3);
    }

    public List<AccidentStatus> getStatuses() {
        return statuses.values().stream().toList();
    }

    public Optional<AccidentStatus> findById(int id) {
        return Optional.ofNullable(statuses.get(id));
    }

}
