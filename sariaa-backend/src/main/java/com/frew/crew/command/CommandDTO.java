package com.frew.crew.command;

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
public class CommandDTO {
  private UUID id;
  private BigDecimal totalPrice;
  private LocalDate commandDate;
  private UserSummaryDTO user; // Reference to a summary of User to avoid recursion
}
