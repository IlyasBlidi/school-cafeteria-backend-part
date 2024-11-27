package com.frew.crew.card;

import com.frew.crew.user.UserSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
  private UUID id;
  private BigDecimal balance;
  private LocalDate lastUpdateDate;
  private UserSummaryDTO user; // Reference to a summary of User to avoid recursion
}
