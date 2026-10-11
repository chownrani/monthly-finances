package com.chownrani.finances.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "servico_competencia", uniqueConstraints = @UniqueConstraint(name = "uk_servico_competencia", columnNames = {
        "competencia_id", "servico_id" }))
@Getter
@Setter
@NoArgsConstructor
public class ServicoCompetencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "competencia_id", nullable = false)
    private Competencia competence;

    @ManyToOne(optional = false)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico service;

    @Column(name = "valor", nullable = false)
    private BigDecimal amount;

    @Column(name = "pago", nullable = false)
    private Boolean paid = Boolean.FALSE;
}
