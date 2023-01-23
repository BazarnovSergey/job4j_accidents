package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель статуса нарушения
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentStatus {
    /**
     * Идентификатор статуса
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Статус нарушения
     */
    private String status;
}
