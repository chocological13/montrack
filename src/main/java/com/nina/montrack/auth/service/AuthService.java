package com.nina.montrack.auth.service;


import java.util.Map;
import org.springframework.security.core.Authentication;

public interface AuthService {

  String generateToken(Authentication authentication);

//  void logout(Authentication authentication);

  //!exp
  Map<String, Object> decodeToken(String token);
}
