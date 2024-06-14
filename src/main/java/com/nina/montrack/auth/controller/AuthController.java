package com.nina.montrack.auth.controller;

import com.nina.montrack.auth.dto.LoginRequestDto;
import com.nina.montrack.auth.dto.LoginResponseDto;
import com.nina.montrack.auth.entity.AuthUser;
import com.nina.montrack.auth.service.AuthService;
import com.nina.montrack.responses.Response;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
@Log
@Data
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final AuthenticationManager authenticationManager;

  @GetMapping
  public Authentication getPrinciple() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
    log.info("Login request for user: " + loginRequestDto.getUsername());
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
            loginRequestDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    AuthUser userDetails = (AuthUser) authentication.getPrincipal();
    log.info("Token requested for user " + userDetails.getUsername() + " with roles " + userDetails.getAuthorities().toArray()[0]);
    String token = authService.generateToken(authentication);

    LoginResponseDto response = new LoginResponseDto();
    response.setMessage("Successfully logged in");
    response.setToken(token);
    return Response.successfulResponse(HttpStatus.OK.value(), response.getMessage(), response);
  }
}
