package com.chownrani.finances.entities;

import java.math.BigDecimal;

import com.chownrani.finances.entities.abstracts.Allocation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emergency")
@Getter
@Setter
@NoArgsConstructor
public class Emergency extends Allocation {

    @Column(name = "goal", nullable = false)
    private BigDecimal goal;
}
