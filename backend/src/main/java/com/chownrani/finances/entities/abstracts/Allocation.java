package com.chownrani.finances.entities.abstracts;

import java.math.BigDecimal;

import com.chownrani.finances.entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class Allocation extends AbstractEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "income_percentage")
    private BigDecimal incomePercentage;

    @Column(name = "total_saved", nullable = false)
    private BigDecimal totalSaved = BigDecimal.ZERO;
}
