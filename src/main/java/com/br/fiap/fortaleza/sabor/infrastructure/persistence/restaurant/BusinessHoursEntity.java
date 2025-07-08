package com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "horarios")
public class BusinessHoursEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek day;

    @Column(name = "hora_abertura", nullable = false)
    private LocalTime openingTime;

    @Column(name = "hora_fechamento", nullable = false)
    private LocalTime closingTime;

    @Column(name = "observacao", length = 100)
    private String observations;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private RestaurantEntity restaurant;

    public BusinessHoursEntity(DayOfWeek day, LocalTime openingTime, LocalTime closingTime, String observations, RestaurantEntity restaurant) {
        this.day = day;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.observations = observations;
        this.restaurant = restaurant;
    }

    public BusinessHoursEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }
}
