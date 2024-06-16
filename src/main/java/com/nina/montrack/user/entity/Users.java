package com.nina.montrack.user.entity;

import com.nina.montrack.role.entity.Role;
import com.nina.montrack.wallet.entity.Wallet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.proxy.HibernateProxy;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "users")
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
  @SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "username", nullable = false, length = 20, unique = true)
  private String username;

  @Column(name = "email", nullable = false, length = 50, unique = true)
  private String email;

  @Column(name = "password", nullable = false, length = 20)
  private String password;

  @Column(name = "first_name", length = 50)
  private String firstName;

  @Column(name = "last_name", length = 50)
  private String lastName;

  @Column(name = "is_registered", columnDefinition = "Boolean default true")
  @ColumnDefault("true")
  private Boolean isRegistered;

  @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Instant createdAt;

  @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private Instant updatedAt;

  @Column(name = "deleted_at")
  private Instant deletedAt;

  @Column(name = "bio")
  private String bio;

  @Column(name = "avatar")
  private String avatar;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
    updatedAt = Instant.now();
    isRegistered = true;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Instant.now();
  }

  @OneToMany(mappedBy = "user")
  @Exclude
  private List<Wallet> wallets;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name= "users_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private List<Role> roles = new ArrayList<>();

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass = o instanceof HibernateProxy
        ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
        : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    Users users = (Users) o;
    return getId() != null && Objects.equals(getId(), users.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
        .hashCode() : getClass().hashCode();
  }
}
