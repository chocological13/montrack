package com.nina.montrack.user.controller;

import com.nina.montrack.responses.Response;
import com.nina.montrack.user.entity.Users;
import com.nina.montrack.user.service.UsersService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {

  private final UsersService usersService;

  @GetMapping
  public List<Users> getAllUsers() {
    return usersService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response<Users>> getUserById(@PathVariable Long id) {
    return Response.successfulResponse(HttpStatus.ACCEPTED.value(), "Now displaying user Id " + id,
        usersService.findById(id));
  }

  @PostMapping
  public ResponseEntity<Response<Users>> registerUser(@RequestBody Users user) {
    return Response.successfulResponse(HttpStatus.CREATED.value(), "Successfully registered!", usersService.save(user));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response<Users>> updateUser(@PathVariable Long id, @RequestBody Users user) {
    return Response.successfulResponse(HttpStatus.ACCEPTED.value(), "Successfully updated user Id " + id,
        usersService.update(id, user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Response<Users>> softDeleteUser(@PathVariable Long id) {
    return Response.successfulResponse(HttpStatus.OK.value(), "User has been deleted", usersService.softDelete(id));
  }

  @DeleteMapping("/del/{id}")
  public String hardDeleteUser(@PathVariable Long id) {
    usersService.hardDelete(id);
    return "User" + id + " has been deleted";
  }
}
