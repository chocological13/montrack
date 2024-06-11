package com.nina.montrack.wallet.service.impl;

import com.nina.montrack.currency.entity.Currency;
import com.nina.montrack.currency.service.CurrencyService;
import com.nina.montrack.exceptions.DataNotFoundException;
import com.nina.montrack.responses.Response;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

  private final WalletRepository walletRepository;
  private final UsersService usersService;
  private final CurrencyService currencyService;
//  private final WalletMapper walletMapper;

  @Override
  public List<Wallet> getAllWallets() {
    return walletRepository.findAll();
  }

  @Override
  public List<Wallet> getUserWallets(Long userId) {
    Optional<Users> user = usersService.findById(userId);
    if (user.isPresent()) {
      return user.get().getWallets();
    } else {
      throw new DataNotFoundException("User not found");
    }
  }

  @Override
  public Wallet getWallet(Long userId, Long walletId) {
    if (usersService.findById(userId).isPresent()) {
      Optional<Wallet> walletOptional = walletRepository.findById(walletId);
      if (walletOptional.isPresent()) {
        return walletOptional.get();
      } else {
        throw new DataNotFoundException("Wallet not found");
      }
    }
    throw new DataNotFoundException("User not found");
  }

  @Override
  public Wallet addUserWallet(WalletDto request) {
    Optional<Users> userOptional = usersService.findById(request.getUser());
    Optional<Currency> currencyOptional = currencyService.findById(request.getCurrency());
    if (userOptional.isEmpty()) {
      return null;
    } else {
      Wallet newWallet = new Wallet();
      return setWallet(newWallet, request);
//      return walletRepository.save(walletMapper.toWallet(request)); ?????
    }
  }

  @Override
  public Wallet updateUserWallet(WalletDto request) {
    Optional<Users> user = usersService.findById(request.getUser());
    if (user.isPresent()) {
      Currency currency = currencyService.findById(request.getCurrency()).orElse(null);
      Wallet editWallet = walletRepository.findById(request.getId()).orElse(null);
      return setWallet(editWallet, request);
    } else {
      throw new DataNotFoundException("User not found");
    }
  }

  private Wallet setWallet(Wallet newWallet, WalletDto request) {
    newWallet.setUser(usersService.getById(request.getUser()));
    newWallet.setWalletName(request.getWalletName());
    newWallet.setCurrency(currencyService.findById(request.getCurrency()).orElse(null));
    newWallet.setBalance(request.getBalance());
    newWallet.setIsDefault(request.getIsDefault());
    newWallet.setDeletedAt(request.getDeletedAt());
    newWallet.setIsActive(request.getIsActive());
    return walletRepository.save(newWallet);
  }

  @Override
  public ResponseEntity<Response<Wallet>> setActiveWallet(Long userId, Long walletId) {
    Wallet thisWallet = getWallet(userId, walletId);
    List<Wallet> walletList = getUserWallets(userId);

    // set all wallets as false
    for (Wallet each: walletList) {
      each.setIsActive(false);
      walletRepository.save(each);
    }

    // set this current one active
    thisWallet.setIsActive(true);
    walletRepository.save(thisWallet);

    return Response.successfulResponse(HttpStatus.OK.value(), "Wallet updated successfully", thisWallet);
  }
}
