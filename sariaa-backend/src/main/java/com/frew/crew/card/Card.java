package com.frew.crew.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frew.crew.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(
    precision = 10,
    scale = 2,
    nullable = false
  )
  private BigDecimal balance;

  @Column(nullable = false)
  private LocalDate lastUpdateDate;

  @JsonIgnore
  @OneToOne(
    mappedBy = "card",
    optional = false
  )
  private User user;
}
