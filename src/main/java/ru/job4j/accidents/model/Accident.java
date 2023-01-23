package ru.job4j.accidents.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Модель нарушения
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Accident {
    /**
     * Идентификатор нарушения
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Название нарушения
     */
    private String name;

    /**
     * Описание нарушения
     */
    private String text;

    /**
     * Номер автомобиля
     */
    private String carNumber;

    /**
     * Тип нарушения
     */
    @ToString.Exclude
    private AccidentType type;

    /**
     * Статьи нарушений
     */
    @ToString.Exclude
    private Set<Rule> rules;

    /**
     * Адрес нарушения
     */
    private String address;

    /**
     * Статус нарушения
     */
    @ToString.Exclude
    private AccidentStatus accidentStatus;

    /**
     * Фото нарушения
     */
    private byte[] photo;

    /**
     * Дата и время нарушения
     */
    private LocalDateTime dateAccident = LocalDateTime.now();
}