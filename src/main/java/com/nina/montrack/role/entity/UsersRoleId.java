package com.nina.montrack.role.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Embeddable
public class UsersRoleId implements Serializable {

  private static final long serialVersionUID = 6930877516135547192L;
  @NotNull
  @Column(name = "user_id", nullable = false)
  private Long userId;

  @NotNull
  @Column(name = "role_id", nullable = false)
  private Long roleId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    UsersRoleId entity = (UsersRoleId) o;
    return Objects.equals(this.roleId, entity.roleId) &&
        Objects.equals(this.userId, entity.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roleId, userId);
  }

}