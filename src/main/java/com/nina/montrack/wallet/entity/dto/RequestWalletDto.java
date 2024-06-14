package com.nina.montrack.wallet.entity.dto;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RequestWalletDto {
  private Long id;
  private Long user;
  private String walletName;
  private Long currency;
  private BigDecimal balance;
  private Boolean isDefault;
  private Instant deletedAt;
  private Boolean isActive;
}
