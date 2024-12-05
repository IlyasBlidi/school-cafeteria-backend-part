package com.frew.crew.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardBodyDTO {
  private UUID cardId;
  private BigDecimal balance;
  private LocalDate lastUpdateDate;
}
