package com.chownrani.finances.entities;

import com.chownrani.finances.entities.abstracts.Allocation;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "investment")
@Getter
@Setter
@NoArgsConstructor
public class Investment extends Allocation {
}
