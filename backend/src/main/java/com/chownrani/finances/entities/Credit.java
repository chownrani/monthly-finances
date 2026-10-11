package com.chownrani.finances.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credit")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
public class Credit extends Card {

    @Column(name = "dia_fechamento", nullable = false)
    private Integer closingDay;

    @Column(name = "dia_vencimento", nullable = false)
    private Integer dueDay;
}
