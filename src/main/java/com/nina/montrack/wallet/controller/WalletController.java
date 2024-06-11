package com.nina.montrack.wallet.controller;

import com.nina.montrack.wallet.entity.dto.WalletDto;
import com.nina.montrack.responses.Response;
import com.nina.montrack.wallet.entity.Wallet;
import com.nina.montrack.wallet.service.WalletService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class WalletController {

  private final WalletService walletService;

  @GetMapping("/wallets")
  public List<Wallet> getWallets() {
    return walletService.getWallets();
  }

  @GetMapping("/users/{userId}/wallets/{id}")
  public ResponseEntity<Response<List<Wallet>>> getUserWallets(@PathVariable Long userId, @PathVariable Long id) {
    List<Wallet> usersWallets = walletService.getUserWallets(userId, id);
    if (usersWallets.isEmpty()) {
      return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Wallet for user ID " + userId + " not found");
    } else {
      return Response.successfulResponse(HttpStatus.FOUND.value(),
          "Displaying wallets for user ID " + userId, usersWallets);
    }
  }

  @PostMapping("/users/{userId}/wallets")
  public ResponseEntity<Response<Wallet>> createUserWallet(@PathVariable Long userId, @RequestBody WalletDto request)  {
    Wallet newWallet = walletService.addUserWallet(request);
    return Response.successfulResponse(HttpStatus.CREATED.value(), "success", newWallet);
  }

//  @PutMapping("/users/{userId}/wallets/{id}")
//  public ResponseEntity<Response<Wallet>> updateUserWallet(@PathVariable Long userId,
//      @PathVariable Long id, @RequestBody WalletDto request) {
//    Wallet wallet = (Wallet) walletService.getUserWallets(userId, id);
//    if (wallet)
//  }
}
