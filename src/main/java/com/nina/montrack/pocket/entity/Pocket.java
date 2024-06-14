package com.nina.montrack.pocket.entity;

import com.nina.montrack.currency.entity.Currency;
import com.nina.montrack.user.entity.Users;
import com.nina.montrack.wallet.entity.Wallet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pocket", schema = "public")
public class Pocket {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pocket_id_gen")
  @SequenceGenerator(name = "pocket_id_gen", sequenceName = "pocket_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull(message = "Pocket must have a name")
  @Column(name = "pocket_name", length = 50, nullable = false)
  private String pocketName;

  @Column(name = "description", length = Integer.MAX_VALUE)
  private String description;

  @NotNull(message = "Please provide budget amount")
  @Column(name = "limit_amount")
  private BigDecimal limitAmount;

  @Column(name = "used_amount")
  private BigDecimal usedAmount;

  @NotNull(message = "Please define start date")
  @Column(name = "start_date")
  private Instant startDate;

  @NotNull(message = "Please define end date")
  @Column(name = "end_date")
  private Instant endDate;

  @Column(name = "is_active")
  private Boolean isActive;

  @NotNull(message = "Must be tied to a wallet")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wallet_id", nullable = false)
  private Wallet wallet;

  @Column(name = "emoji")
  private String emoji;

  @Column(name = "deleted_at")
  private Instant deletedAt;

}