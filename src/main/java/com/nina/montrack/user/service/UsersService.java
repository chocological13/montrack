package com.nina.montrack.user.service;

import com.nina.montrack.user.entity.Users;
import com.nina.montrack.user.entity.dto.UserProfileDTO;
import java.util.List;
import java.util.Optional;

public interface UsersService {

  List<Users> findAll();

  Users getById(Long id);

  Optional<Users> findById(Long id);

  Optional<Users> findByEmail(String email);

  Optional<Users> findByUsername(String username);

  Users save(Users user);

  Users update(Long id, Users user);

  Users saveUserProfile(Long id, UserProfileDTO userProfileDTO);

  Users softDelete(Long id);

  void hardDelete(Long id);
}
