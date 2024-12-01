package com.frew.crew.command;

import com.frew.crew.article.ArticleDTO;
import com.frew.crew.user.UserSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandDetailsDTO {
  private UUID commandId;
  private BigDecimal totalPrice;
  private LocalDate commandDate;
  private UserSummaryDTO user;
  private List<ArticleDTO> articles;
}
