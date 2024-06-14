package com.nina.montrack.goal.entity;

import com.nina.montrack.user.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "goal", schema = "public")
public class Goal {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "goal_id_gen")
  @SequenceGenerator(name = "goal_id_gen", sequenceName = "goal_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private Users user;

  @Size(max = 20)
  @NotNull
  @Column(name = "name", nullable = false, length = 20)
  private String name;

  @Column(name = "description", length = Integer.MAX_VALUE)
  private String description;

  @Size(max = 3)
  @Column(name = "currency", length = 3)
  private String currency;

  @NotNull
  @Column(name = "target_amount", nullable = false)
  private BigDecimal targetAmount;

  @Column(name = "due_date")
  private Instant dueDate;

  @Column(name = "current_balance")
  private BigDecimal currentBalance;

}