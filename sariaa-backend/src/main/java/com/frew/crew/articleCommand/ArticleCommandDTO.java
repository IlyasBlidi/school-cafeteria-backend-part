package com.frew.crew.articleCommand;

import com.frew.crew.article.Article;
import com.frew.crew.command.CommandDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommandDTO {
  private ArticleCommandId id;
  private Article article;
  private CommandDTO command;
  private Integer quantity;
}
