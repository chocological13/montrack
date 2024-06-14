package com.nina.montrack.wallet.service;

import com.nina.montrack.responses.Response;
import com.nina.montrack.wallet.entity.dto.RequestWalletDto;
import com.nina.montrack.wallet.entity.Wallet;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface WalletService {

  List<Wallet> getAllWallets();

  List<Wallet> getUserWallets(Long userId);

  Wallet getWallet(Long userId, Long walletId);

  Wallet addUserWallet(RequestWalletDto request);

  Wallet updateUserWallet(RequestWalletDto request);

  ResponseEntity<Response<Wallet>> setActiveWallet(Long userId, Long walletId);

}
