package com.nina.montrack.auth.entity;

import com.nina.montrack.user.entity.Users;
import java.util.ArrayList;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class AuthUser implements UserDetails {

  private final Users user;

  public AuthUser(Users user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(() -> "ROLE_USER");
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

 @Override
 public boolean isCredentialsNonExpired() {
    return true;
 }

 @Override
  public boolean isEnabled() {
    return true;
 }
}
