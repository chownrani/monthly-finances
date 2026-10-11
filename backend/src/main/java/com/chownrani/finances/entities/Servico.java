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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "servico")
@Getter
@Setter
@NoArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_gasto_fixo_id", nullable = false)
    private CategoriaGastoFixo fixedExpenseCategory;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "valor_padrao", nullable = false)
    private BigDecimal defaultValue;

    @Column(name = "dia_vencimento")
    private Integer dueDay;

    @Column(name = "ativo", nullable = false)
    private Boolean active = Boolean.TRUE;
}
