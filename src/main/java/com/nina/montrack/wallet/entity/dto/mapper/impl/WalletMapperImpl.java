package com.nina.montrack.wallet.entity.dto.mapper.impl;

import com.nina.montrack.currency.entity.Currency;
import com.nina.montrack.currency.service.CurrencyService;
import com.nina.montrack.user.entity.Users;
import com.nina.montrack.user.service.UsersService;
import com.nina.montrack.wallet.entity.dto.WalletDto;
import com.nina.montrack.wallet.entity.Wallet;
import com.nina.montrack.wallet.entity.dto.mapper.WalletMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WalletMapperImpl implements WalletMapper {

  private final UsersService usersService;
  private final CurrencyService currencyService;

  @Override
  public Users mapUser(Long id) {
    return usersService.getById(id);
  }

  @Override
  public Currency mapCurrency(Long id) {
    return currencyService.findById(id).orElse(null);
  }

  @Override
  public Wallet toWallet(WalletDto walletDto) {

    Wallet wallet = new Wallet();
    wallet.setUser(mapUser(walletDto.getUser()));
    wallet.setWalletName(walletDto.getWalletName());
    wallet.setCurrency(mapCurrency(walletDto.getCurrency()));
    wallet.setBalance(walletDto.getBalance());
    wallet.setIsDefault(walletDto.getIsDefault());
    wallet.setDeletedAt(walletDto.getDeletedAt());
    return wallet;
  }
}
