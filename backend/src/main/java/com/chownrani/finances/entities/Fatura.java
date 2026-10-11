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
@Table(name = "fatura", uniqueConstraints = @UniqueConstraint(name = "uk_fatura_competencia_credit", columnNames = {
        "competencia_id", "credit_id" }))
@Getter
@Setter
@NoArgsConstructor
public class Fatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "competencia_id", nullable = false)
    private Competencia competencia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "credit_id", nullable = false)
    private Credit credit;

    @Column(nullable = false)
    private BigDecimal valor;
}
