package com.chownrani.finances.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "competencia", uniqueConstraints = @UniqueConstraint(name = "uk_competencia_user_ano_mes", columnNames = {
        "user_id", "ano", "mes" }))
@Getter
@Setter
@NoArgsConstructor
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "ano", nullable = false)
    private Integer year;

    @Column(name = "mes", nullable = false)
    private Integer month;

    @Column(name = "renda")
    private BigDecimal income;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCompetencia status = StatusCompetencia.ABERTO;

    @Column(name = "teto_variavel")
    private BigDecimal variableLimit;

    @Column(name = "sobra")
    private BigDecimal surplus;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "fechado_em")
    private LocalDateTime closedAt;
}
