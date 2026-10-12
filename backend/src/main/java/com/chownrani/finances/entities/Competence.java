package com.chownrani.finances.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.chownrani.finances.entities.abstracts.AbstractEntity;
import com.chownrani.finances.entities.enums.CompetenceStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "competence", uniqueConstraints = @UniqueConstraint(name = "uk_competence_user_year_month", columnNames = {
        "user_id", "year", "month" }))
@Getter
@Setter
@NoArgsConstructor
public class Competence extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "month", nullable = false)
    private Integer month;

    @Column(name = "income")
    private BigDecimal income;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompetenceStatus status = CompetenceStatus.OPEN;

    @Column(name = "variable_limit")
    private BigDecimal variableLimit;

    @Column(name = "surplus")
    private BigDecimal surplus;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;
}
