package com.nina.montrack.wallet.service;

import com.nina.montrack.wallet.entity.dto.WalletDto;
import com.nina.montrack.wallet.entity.Wallet;
import java.util.List;

public interface WalletService {

  List<Wallet> getAllWallets();

  List<Wallet> getUserWallets(Long userId);

  Wallet getWallet(Long userId, Long walletId);

  Wallet addUserWallet(WalletDto request);

  Wallet updateUserWallet(WalletDto request);
}
