package com.nina.montrack.currency.service.impl;

import com.nina.montrack.currency.entity.Currency;
import com.nina.montrack.currency.repository.CurrencyRepository;
import com.nina.montrack.currency.service.CurrencyService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

  private final CurrencyRepository currencyRepository;

  @Override
  public Optional<Currency> findById(Long id) {
    return currencyRepository.findById(id);
  }
}
