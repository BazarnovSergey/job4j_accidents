package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель типа инцидента
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentType {
    /**
     * Идентификатор типа инцидента
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Название типа инцидента
     */
    private String name;
}