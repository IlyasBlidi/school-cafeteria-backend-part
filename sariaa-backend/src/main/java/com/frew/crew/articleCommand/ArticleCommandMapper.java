package com.frew.crew.articleCommand;

import com.frew.crew.command.CommandMapper;

public class ArticleCommandMapper {
  public static ArticleCommandDTO toArticleCommandDTO(ArticleCommand articleCommand) {
    return new ArticleCommandDTO(
      articleCommand.getId(),
      articleCommand.getArticle(),
      CommandMapper.toCommandDTO(articleCommand.getCommand()),
      articleCommand.getQuantity()
    );
  }
}
