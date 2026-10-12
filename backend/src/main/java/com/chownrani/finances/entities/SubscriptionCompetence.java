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
@Table(name = "subscription_competence", uniqueConstraints = @UniqueConstraint(name = "uk_subscription_competence", columnNames = {
        "competence_id", "subscription_id" }))
@Getter
@Setter
@NoArgsConstructor
public class SubscriptionCompetence extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "competence_id", nullable = false)
    private Competence competence;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "paid", nullable = false)
    private Boolean paid = Boolean.FALSE;
}
