package com.chownrani.finances.entities;

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
@Table(name = "categoria_gasto_variavel")
@Getter
@Setter
@NoArgsConstructor
public class CategoriaGastoVariavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "cor")
    private String color;

    @Column(name = "ativa", nullable = false)
    private Boolean active = Boolean.TRUE;
}
