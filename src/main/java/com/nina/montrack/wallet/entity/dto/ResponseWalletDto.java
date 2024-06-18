package com.nina.montrack.wallet.entity.dto;

import com.nina.montrack.wallet.entity.Wallet;
import lombok.Data;

@Data
public class ResponseWalletDto {

  private Long id;
  private String walletName;
  private String currency;

  public ResponseWalletDto(Wallet wallet) {
    this.id = wallet.getId();
    this.walletName = wallet.getWalletName();
//  this.currency = wallet.getCurrency()
  }
}
