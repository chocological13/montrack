package com.nina.montrack.wallet.entity;

import com.nina.montrack.currency.entity.Currency;
import com.nina.montrack.user.entity.Users;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallet")
public class Wallet {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_id_gen")
  @SequenceGenerator(name = "wallet_id_gen", sequenceName = "wallet_id_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private Users user;

  @Column(name = "wallet_name", nullable = false, length = 50)
  private String walletName;

  @JoinColumn(name = "currency_id", referencedColumnName = "id")
  @OneToOne(cascade = CascadeType.MERGE)
  @ColumnDefault("1")
  private Currency currency;

  @Column(name = "balance")
  @ColumnDefault("0.00")
  private BigDecimal balance;

  @Column(name = "is_default")
  private Boolean isDefault;

  @Column(name = "created_at", updatable = false)
  @ColumnDefault("CURRENT_TIMESTAMP")
  private Instant createdAt;

  @Column(name = "updated_at")
  @ColumnDefault("CURRENT_TIMESTAMP")
  private Instant updatedAt;

  @Column(name = "deleted_at")
  private Instant deletedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
    updatedAt = Instant.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Instant.now();
  }
}
