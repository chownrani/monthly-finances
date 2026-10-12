package com.chownrani.finances.entities;

import java.math.BigDecimal;

import com.chownrani.finances.entities.abstracts.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor
public class Subscription extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "fixed_expense_category_id", nullable = false)
    private FixedExpenseCategory fixedExpenseCategory;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "default_value", nullable = false)
    private BigDecimal defaultValue;

    @Column(name = "due_day")
    private Integer dueDay;

    @Column(name = "active", nullable = false)
    private Boolean active = Boolean.TRUE;
}
