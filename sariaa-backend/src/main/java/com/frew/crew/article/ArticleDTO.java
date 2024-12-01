package com.frew.crew.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
  private UUID articleId;
  private String title;
  private String description;
  private BigDecimal price;
  private Integer quantity;
}
