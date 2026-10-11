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
@Table(name = "gasto_variavel_competencia", uniqueConstraints = @UniqueConstraint(name = "uk_gasto_variavel_competencia_categoria", columnNames = {
        "competencia_id", "categoria_id" }))
@Getter
@Setter
@NoArgsConstructor
public class GastoVariavelCompetencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "competencia_id", nullable = false)
    private Competencia competencia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaGastoVariavel categoria;

    @Column(name = "valor_acumulado", nullable = false)
    private BigDecimal valorAcumulado = BigDecimal.ZERO;
}
