package com.nina.montrack.wallet.service.impl;

import com.nina.montrack.currency.entity.Currency;
import com.nina.montrack.currency.service.CurrencyService;
import com.nina.montrack.exceptions.DataNotFoundException;
import com.nina.montrack.wallet.entity.dto.WalletDto;
import com.nina.montrack.user.entity.Users;
import com.nina.montrack.user.service.UsersService;
import com.nina.montrack.wallet.entity.Wallet;
//import com.nina.montrack.wallet.entity.dto.mapper.WalletMapper;
import com.nina.montrack.wallet.repository.WalletRepository;
import com.nina.montrack.wallet.service.WalletService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

  private final WalletRepository walletRepository;
  private final UsersService usersService;
  private final CurrencyService currencyService;
//  private final WalletMapper walletMapper;

  @Override
  public List<Wallet> getWallets() {
    return walletRepository.findAll();
  }

  @Override
  public List<Wallet> getUserWallets(Long userId) {
    Optional<Users> checkUser = usersService.optFindById(userId);
    if (checkUser.isEmpty()) {
      throw new DataNotFoundException("User not found");
    } else {
      return walletRepository.findByUserId(userId);
    }
  }

  @Override
  public Wallet addUserWallet(WalletDto request) {
    Optional<Users> userOptional = usersService.optFindById(request.getUser());
    Optional<Currency> currencyOptional = currencyService.findById(request.getCurrency());
    if (userOptional.isEmpty()) {
      return null;
    } else {
      Wallet newWallet = new Wallet();
      newWallet.setUser(userOptional.get());
      newWallet.setWalletName(request.getWalletName());
      newWallet.setCurrency(currencyOptional.orElse(null));
      newWallet.setBalance(request.getBalance());
      newWallet.setIsDefault(request.getIsDefault());
      return walletRepository.save(newWallet);
//      return walletRepository.save(walletMapper.toWallet(request)); ?????
    }
  }

//  @Override
//  public Wallet editUserWallet(WalletDto request) {
//    Users user = usersService.findById(request.getUser());
//    Currency currency = currencyService.findById(request.getCurrency()).orElse(null);
//    Wallet editWallet = new Wallet();
//    editWallet.setUser(user);
//    editWallet.setWalletName(request.getWalletName());
//    editWallet.setCurrency(currency);
//    editWallet.setBalance(request.getBalance());
//    editWallet.setIsDefault(request.getIsDefault());
//    editWallet.setDeletedAt(request.getDeletedAt());
//    return walletRepository.save(editWallet);
//  }

//  @Override
//  public Wallet editUserWallet(UpdateWalletRequest request) {
//
//  }
}
