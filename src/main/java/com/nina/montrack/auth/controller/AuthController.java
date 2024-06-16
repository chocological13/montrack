package com.nina.montrack.auth.controller;

import com.nina.montrack.auth.dto.login.LoginRequestDto;
import com.nina.montrack.auth.dto.login.LoginResponseDto;
import com.nina.montrack.auth.dto.register.RegisterRequestDto;
import com.nina.montrack.auth.entity.AuthUser;
import com.nina.montrack.auth.service.AuthService;
import com.nina.montrack.exceptions.DataNotFoundException;
import com.nina.montrack.responses.Response;
import com.nina.montrack.role.entity.Role;
import com.nina.montrack.role.repository.RoleRepository;
import com.nina.montrack.user.entity.Users;
import com.nina.montrack.user.repository.UsersRepository;
import jakarta.servlet.http.Cookie;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
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
  private final UsersRepository usersRepository;
  private final RoleRepository roleRepository;

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

    var ctx = SecurityContextHolder.getContext();
    ctx.setAuthentication(authentication);

    AuthUser userDetails = (AuthUser) authentication.getPrincipal();
    log.info("Token requested for user " + userDetails.getUsername() + " with roles " + userDetails.getAuthorities()
        .toArray()[0]);
    String token = authService.generateToken(authentication);

    LoginResponseDto response = new LoginResponseDto();
    response.setMessage("Successfully logged in");
    response.setToken(token);

    Cookie cookie = new Cookie("token", token);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Set-Cookie", cookie.getName() + "=" + cookie.getValue() + "; Path=/; HttpOnly");
    return ResponseEntity.ok().headers(headers).body(response);
  }

  @PostMapping("/register")
  public ResponseEntity<Response<Object>> register(@RequestBody RegisterRequestDto registerRequestDto) {
    log.info("Requesting login for: " + registerRequestDto.getUsername());

    // check if user or email exists
    Optional<Users> usersOptional = usersRepository.findByUsername(registerRequestDto.getUsername());
    Optional<Users> usersOptional2 = usersRepository.findByEmail(registerRequestDto.getEmail());
    if (usersOptional.isPresent() || usersOptional2.isPresent()) {
      return Response.failedResponse(HttpStatus.BAD_REQUEST.value(),"Username or E-mail already exist. Please enter "
          + "another credentials");
    }

    Users user = registerRequestDto.toEntity();

    // Give all registering user the basic user role for now
    Role roles = roleRepository.findByRole("user").orElse(null);
    user.setRoles(Collections.singletonList(roles));

    // save user to repo
    usersRepository.save(user);

    return Response.successfulResponse(HttpStatus.CREATED.value(),"User registered successfully", user);

  }
}
