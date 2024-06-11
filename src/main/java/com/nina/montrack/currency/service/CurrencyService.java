package com.nina.montrack.currency.service;

import com.nina.montrack.currency.entity.Currency;
import java.util.Optional;

public interface CurrencyService {

  Optional<Currency> findById(Long id);
}
