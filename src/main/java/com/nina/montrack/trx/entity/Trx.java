package com.nina.montrack.trx.entity;

import com.nina.montrack.currency.entity.Currency;
import com.nina.montrack.goal.entity.Goal;
import com.nina.montrack.pocket.entity.Pocket;
import com.nina.montrack.user.entity.Users;
import com.nina.montrack.wallet.entity.Wallet;
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
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trx", schema = "public")
public class Trx {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trx_id_gen")
  @SequenceGenerator(name = "trx_id_gen", sequenceName = "trx_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wallet_id", referencedColumnName = "id", nullable = false)
  private Wallet wallet;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "transfer_from_wallet_id", referencedColumnName = "id")
  private Wallet transferFromWallet;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "transfer_to_wallet_id", referencedColumnName = "id")
  private Wallet transferToWallet;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "goal_id", referencedColumnName = "id")
  private Goal goal;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "pocket_id", referencedColumnName = "id")
  private Pocket pocket;

  @Size(max = 3)
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
  private Currency currency;

  @NotNull
  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "date", nullable = false)
  private Instant date;

  @Size(max = 50)
  @Column(name = "category", length = 50)
  private String category;

  @Column(name = "description", length = Integer.MAX_VALUE)
  private String description;

  @Column(name = "is_canceled")
  private Boolean isCanceled;
}