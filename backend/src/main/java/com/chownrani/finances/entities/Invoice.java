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
@Table(name = "invoice", uniqueConstraints = @UniqueConstraint(name = "uk_invoice_competence_credit", columnNames = {
        "competence_id", "credit_id" }))
@Getter
@Setter
@NoArgsConstructor
public class Invoice extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "competence_id", nullable = false)
    private Competence competence;

    @ManyToOne(optional = false)
    @JoinColumn(name = "credit_id", nullable = false)
    private Credit credit;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
}
