package com.nina.montrack.config;

import com.nina.montrack.role.entity.Role;
import com.nina.montrack.user.entity.Users;
import com.nina.montrack.user.repository.UsersRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    return new User(user.getUsername(), user.getPassword(), mapRoles(user.getRoles()));
  }

  private Collection<GrantedAuthority> mapRoles(List<Role> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole ())).collect(Collectors.toList());
  }
}
