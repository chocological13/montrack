package com.nina.montrack.user.service.impl;

import com.nina.montrack.exceptions.DataNotFoundException;
import com.nina.montrack.user.entity.Users;
import com.nina.montrack.user.entity.dto.UserProfileDTO;
import com.nina.montrack.user.repository.UsersRepository;
import com.nina.montrack.user.service.UsersService;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public List<Users> findAll() {
    return usersRepository.findAll();
  }

  @Override
  public Users getById(Long id) {
    Optional<Users> user = usersRepository.findById(id);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new DataNotFoundException("User with id " + id + " not found.");
    }
  }

  @Override
  public Optional<Users> findById(Long id) {
    return usersRepository.findById(id);
  }

  @Override
  public Optional<Users> findByEmail(String email) {
    return usersRepository.findByEmail(email);
  }

  @Override
  public Optional<Users> findByUsername(String username) {
    return usersRepository.findByUsername(username);
  }

  @Override
  public Users save(Users user) {
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    return usersRepository.save(user);
  }

  @Override
  public Users update(@PathVariable Long id, Users user) {
    Optional<Users> existingUser = usersRepository.findById(id);
    if (existingUser.isPresent()) {
      return usersRepository.save(updateUser(user, existingUser.get()));
    } else {
      throw new RuntimeException("User with id " + user.getId() + " not found.");
    }
  }

  @Override
  public Users saveUserProfile(Long id, UserProfileDTO userProfileDTO) {
    Optional<Users> usersOptional = usersRepository.findById(id);
    if (usersOptional.isPresent()) {
      Users user = usersOptional.get();
      UserProfileDTO userProfile = new UserProfileDTO();
      return usersRepository.save(userProfile.userProfileToUsers(user, userProfileDTO));
    }
    return null;
  }

  @Override
  public Users softDelete(Long id) {
    Optional<Users> user = usersRepository.findById(id);
    if (user.isPresent()) {
      Users userToDelete = user.get();
      userToDelete.setDeletedAt(Instant.now());
      userToDelete.setIsRegistered(false);
      return usersRepository.save(userToDelete);
    } else {
      throw new DataNotFoundException("User with id " + id + " not found.");
    }
  }

  @Override
  public void hardDelete(Long id) {
    Optional<Users> user = usersRepository.findById(id);
    if (user.isPresent()) {
      usersRepository.deleteById(id);
    } else {
      throw new DataNotFoundException("User with id " + id + " not found.");
    }
  }

  private static Users updateUser(Users user, Users updatedUser) {
    updatedUser.setUsername(user.getUsername());
    updatedUser.setEmail(user.getEmail());
    updatedUser.setPassword(user.getPassword());
    updatedUser.setFirstName(user.getFirstName());
    updatedUser.setLastName(user.getLastName());
    updatedUser.setIsRegistered(user.getIsRegistered());
    updatedUser.setDeletedAt(user.getDeletedAt());
    updatedUser.setBio(user.getBio());
    updatedUser.setAvatar(user.getAvatar());
    return updatedUser;
  }
}
