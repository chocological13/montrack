package com.nina.montrack.auth.helpers;

import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class Claims {

  public static Map<String, Object> claims() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication auth = context.getAuthentication();
    Jwt jwt = (Jwt) auth.getPrincipal();
    return jwt.getClaims();
  }
}
