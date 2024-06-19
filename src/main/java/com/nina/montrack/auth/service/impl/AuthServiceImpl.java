package com.nina.montrack.auth.service.impl;

import com.nina.montrack.auth.repository.AuthRedisRepository;
import com.nina.montrack.auth.service.AuthService;
import com.nina.montrack.user.service.UsersService;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Log
@Service
public class AuthServiceImpl implements AuthService {

  private final JwtEncoder jwtEncoder;
  private final JwtDecoder jwtDecoder;
  private final PasswordEncoder passwordEncoder;
  private final UsersService usersService;
  private final AuthRedisRepository authRedisRepository;

  public AuthServiceImpl(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, PasswordEncoder passwordEncoder,
      UsersService usersService,
      AuthRedisRepository authRedisRepository) {
    this.jwtEncoder = jwtEncoder;
    this.jwtDecoder = jwtDecoder;
    this.passwordEncoder = passwordEncoder;
    this.usersService = usersService;
    this.authRedisRepository = authRedisRepository;
  }

  @Override
  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();

    String scope = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(1, ChronoUnit.HOURS))
        .subject(authentication.getName())
        .claim("scope", scope)
        // can add more claims here if you want more data to display
        .build();

    var jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    authRedisRepository.saveJwtKey(authentication.getName(), jwt);
    return jwt;
  }
//
//  @Override
//  public void logout(Authentication authentication) {
//
//  }
//
//  private String

  public Map<String, Object> decodeToken(String token) {
    return jwtDecoder.decode(token).getClaims();
  }

}
