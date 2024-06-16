package com.nina.montrack.auth.controller;


import com.nina.montrack.user.entity.dto.RegisterRequestDto;
import com.nina.montrack.role.entity.Role;
import com.nina.montrack.role.repository.RoleRepository;
import com.nina.montrack.user.entity.Users;
import com.nina.montrack.user.repository.UsersRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AuthController {

  private final UserDetailsService userDetailsService;
  private final AuthenticationManager authenticationManager;
  private final UsersRepository usersRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/users/register")
  public ResponseEntity<String> register(@RequestBody RegisterRequestDto request) {
    if (usersRepository.existsByUsername(request.getUsername())) {
      return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
    }

    Users user = new Users();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    Role role = roleRepository.findByRole("ROLE_USER").orElse(null);
    user.setRoles(Collections.singletonList(role));

    usersRepository.save(user);

    return new ResponseEntity<>("User registration success", HttpStatus.OK);

  }

}
