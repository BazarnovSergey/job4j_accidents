package ru.job4j.accidents.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Модель нарушения
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accident")
public class Accident {
    /**
     * Идентификатор нарушения
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Название нарушения
     */
    @Column(name = "name")
    private String name;

    /**
     * Описание нарушения
     */
    @Column(name = "description_accident")
    private String text;

    /**
     * Номер автомобиля
     */
    @Column(name = "car_number")
    private String carNumber;

    /**
     * Тип нарушения
     */

    @ManyToOne
    @JoinColumn(name = "type_id")
    private AccidentType type;

    /**
     * Статьи нарушений
     */
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "accident_rule",
            joinColumns = @JoinColumn(name = "accident_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id"))
    private Set<Rule> rules = new HashSet<>();

    /**
     * Адрес нарушения
     */
    @Column(name = "address_accident")
    private String address;

    /**
     * Статус нарушения
     */
    @ManyToOne
    @JoinColumn(name = "status_id")
    private AccidentStatus accidentStatus;

    /**
     * Фото нарушения
     */
    @Column(name = "photo")
    private byte[] photo;

    /**
     * Дата и время нарушения
     */
    @Column(name = "date_accident")
    private LocalDateTime dateAccident = LocalDateTime.now();

}