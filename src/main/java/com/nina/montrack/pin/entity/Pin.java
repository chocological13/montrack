package com.nina.montrack.pin.entity;

import com.nina.montrack.user.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pin", schema = "public")
public class Pin {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pin_id_gen")
  @SequenceGenerator(name = "pin_id_gen", sequenceName = "pin_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private Users user;

  @NotNull
  @Column(name = "pin", nullable = false)
  private Integer pin;

}