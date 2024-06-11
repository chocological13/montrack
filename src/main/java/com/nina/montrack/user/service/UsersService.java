package com.nina.montrack.user.service;

import com.nina.montrack.user.entity.Users;
import java.util.List;
import java.util.Optional;

public interface UsersService {

  List<Users> findAll();

  Users getById(Long id);

  Optional<Users> findById(Long id);

  Users save(Users user);

  Users update(Long id, Users user);

  Users softDelete(Long id);

  void hardDelete(Long id);
}
