package com.nina.montrack.currency.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "currency")
public class Currency {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_id_gen")
  @SequenceGenerator(name = "currency_id_gen", sequenceName = "currency_id_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "currency_code", nullable = false)
  private String currencyCode;
}
