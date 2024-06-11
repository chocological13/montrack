package com.nina.montrack.wallet.controller;

import com.nina.montrack.exceptions.DataNotFoundException;
import com.nina.montrack.user.entity.Users;
import com.nina.montrack.user.service.UsersService;
import com.nina.montrack.wallet.entity.dto.WalletDto;
import com.nina.montrack.responses.Response;
import com.nina.montrack.wallet.entity.Wallet;
import com.nina.montrack.wallet.repository.WalletRepository;
import com.nina.montrack.wallet.service.WalletService;
import java.util.List;
import java.util.Optional;
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
  private final UsersService usersService;
  private final WalletRepository walletRepository;

  @GetMapping("/wallets")
  public List<Wallet> getWallets() {
    return walletService.getAllWallets();
  }

  @GetMapping("/users/{userId}/wallets")
  public ResponseEntity<Response<List<Wallet>>> getUserWallets(@PathVariable Long userId) {
    List<Wallet> usersWallets = walletService.getUserWallets(userId);
    return Response.successfulResponse(HttpStatus.FOUND.value(),
        "Displaying wallets for user ID " + userId, usersWallets);
  }

  @GetMapping("/users/{userId}/wallets/{id}")
  public ResponseEntity<Response<Wallet>> getWallet(@PathVariable Long userId, @PathVariable Long id) {
    try {
      Wallet wallet = walletService.getWallet(userId, id);
      return Response.successfulResponse(HttpStatus.OK.value(), "Showing wallet ID " + id
          + " for user ID " + userId, wallet);
    } catch (DataNotFoundException e) {
      return Response.failedResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
  }

  @PostMapping("/users/{userId}/wallets")
  public ResponseEntity<Response<Wallet>> createUserWallet(@PathVariable Long userId, @RequestBody WalletDto request) {
    Wallet newWallet = walletService.addUserWallet(request);
    return Response.successfulResponse(HttpStatus.CREATED.value(), "success", newWallet);
  }

  @PutMapping("/users/{userId}/wallets/{id}")
  public ResponseEntity<Response<Wallet>> updateUserWallet(@PathVariable Long userId,
      @PathVariable Long id, @RequestBody WalletDto request) {
    Optional<Users> usersOptional = usersService.findById(userId);
    if (usersOptional.isPresent()) {
      Wallet updatedWallet = walletService.updateUserWallet(request);
      return Response.successfulResponse(HttpStatus.ACCEPTED.value(), "Wallet successfully updated!", updatedWallet);
    } else {
      return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "User not found!");
    }
  }

  // set active
  @PutMapping("/users/{userId}/wallets/{walletId}/active")
  public ResponseEntity<Response<Wallet>> setActiveWallet(@PathVariable Long userId, @PathVariable Long walletId) {
    return walletService.setActiveWallet(userId, walletId);
  }
}
