package com.nina.montrack.auth.controller;

import com.nina.montrack.auth.entity.dto.forgorPassword.ForgotPasswordRequestDTO;
import com.nina.montrack.auth.entity.dto.forgorPassword.ForgotPasswordResponseDTO;
import com.nina.montrack.auth.entity.dto.login.LoginRequestDto;
import com.nina.montrack.auth.entity.dto.login.LoginResponseDto;
import com.nina.montrack.auth.entity.dto.register.RegisterRequestDto;
import com.nina.montrack.auth.entity.AuthUser;
import com.nina.montrack.auth.repository.AuthRedisRepository;
import com.nina.montrack.auth.service.AuthService;
import com.nina.montrack.responses.Response;
import com.nina.montrack.role.entity.Role;
import com.nina.montrack.role.repository.RoleRepository;
import com.nina.montrack.user.entity.Users;
import com.nina.montrack.user.service.UsersService;
import jakarta.servlet.http.Cookie;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
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
import org.springframework.web.bind.annotation.RequestParam;
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
  private final UsersService usersService;
  private final RoleRepository roleRepository;
  private final AuthRedisRepository authRedisRepository;

  // check currently logged-in user
  @GetMapping
  public Object getPrinciple() {
    return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  // login
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

  // register
  @PostMapping("/register")
  public ResponseEntity<Response<Object>> register(@RequestBody RegisterRequestDto registerRequestDto) {
    log.info("Requesting login for: " + registerRequestDto.getUsername());

    // check if user or email exists
    Optional<Users> usersOptional = usersService.findByUsername(registerRequestDto.getUsername());
    Optional<Users> usersOptional2 = usersService.findByEmail(registerRequestDto.getEmail());
    if (usersOptional.isPresent() || usersOptional2.isPresent()) {
      return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Username or E-mail already exist. Please enter "
          + "another credentials");
    }

    Users user = registerRequestDto.toEntity();

    // Give all registering user the basic user role for now
    Role roles = roleRepository.findByRole("user").orElse(null);
    user.setRoles(Collections.singletonList(roles));

    // save user to repo
    usersService.save(user);

    return Response.successfulResponse(HttpStatus.CREATED.value(), "User registered successfully", user);

  }

  // forgot password
  @PostMapping("/forgot-password")
  public ResponseEntity<Response<ForgotPasswordResponseDTO>> forgotPassword(
      @RequestBody ForgotPasswordRequestDTO request) {
    // check if user exists
    Optional<Users> user = usersService.findByEmail(request.getEmail());
    if (user.isEmpty()) {
      return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "User not found");
    }

    // Generate a random token for password reset
    String resetToken = UUID.randomUUID().toString();
    String tokenIdentifier = user.get().getUsername();

    // save token in redis
    authRedisRepository.saveJwtKey(tokenIdentifier, resetToken);

    // reset password link
    // ! TODO: make reset password link (other end point?)
    String resetTokenURL = "http://localhost:8080/api/v1/auth/reset-password?user=" + tokenIdentifier;

    // ! TODO: send email with the reset password link

    ForgotPasswordResponseDTO response = new ForgotPasswordResponseDTO();
    response.setMessage("Click the link below to reset your password");
    response.setResetTokenURL(resetTokenURL);

    return Response.successfulResponse(response);
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Response<Object>> resetPassword(@RequestParam String user,
      @RequestBody String newPassword) {
    // check if token is valid
    String token = authRedisRepository.getJwtKey(user);
    if (authRedisRepository.isValid(user, token)) {
      Optional<Users> userOptional = usersService.findByUsername(user);
      Users updateUser = userOptional.get();
      updateUser.setPassword(newPassword);
      usersService.save(updateUser);
      return Response.successfulResponse(HttpStatus.OK.value(), "Password changed successfully");
    } else {
      return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Link invalid");
    }
  }
}
