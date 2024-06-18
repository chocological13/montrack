package com.nina.montrack.auth.service.impl;

import com.nina.montrack.auth.entity.AuthUser;
import com.nina.montrack.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AuthUser user = usersRepository
        .findByUsername(username)
        .map(AuthUser::new)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    return user;
  }

}
