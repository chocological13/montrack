package com.nina.montrack.wallet.entity.dto.mapper;

import com.nina.montrack.currency.entity.Currency;
import com.nina.montrack.user.entity.Users;
import com.nina.montrack.wallet.entity.dto.WalletDto;
import com.nina.montrack.wallet.entity.Wallet;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface WalletMapper {

  Users mapUser(Long id);

  Currency mapCurrency(Long id);

  Wallet toWallet(WalletDto walletDto);
}
