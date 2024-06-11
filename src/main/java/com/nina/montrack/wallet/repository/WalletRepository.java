package com.nina.montrack.wallet.repository;

import com.nina.montrack.user.entity.Users;
import com.nina.montrack.wallet.entity.Wallet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

  List<Wallet> findByUserIdAndId(Long userId, Long id);
}
