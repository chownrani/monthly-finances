package com.chownrani.finances.entities;

import java.math.BigDecimal;

import com.chownrani.finances.entities.abstracts.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "variable_expense_competence", uniqueConstraints = @UniqueConstraint(name = "uk_variable_expense_competence_category", columnNames = {
        "competence_id", "category_id" }))
@Getter
@Setter
@NoArgsConstructor
public class VariableExpenseCompetence extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "competence_id", nullable = false)
    private Competence competence;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private VariableExpenseCategory category;

    @Column(name = "accumulated_amount", nullable = false)
    private BigDecimal accumulatedAmount = BigDecimal.ZERO;
}
