package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель описывающая статьи нарушений
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rule {
    /**
     * Идентификатор статьи нарушения
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Название статьи нарушения
     */
    private String name;

}
