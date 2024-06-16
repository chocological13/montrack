package com.nina.montrack.role.entity;

import com.nina.montrack.user.entity.Users;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users_role", schema = "public")
public class UsersRole {

  @SequenceGenerator(name = "users_role_id_gen", sequenceName = "role_id_seq", allocationSize = 1)
  @EmbeddedId
  private UsersRoleId id;

  @MapsId("userId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private Users user;

  @MapsId("roleId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

}