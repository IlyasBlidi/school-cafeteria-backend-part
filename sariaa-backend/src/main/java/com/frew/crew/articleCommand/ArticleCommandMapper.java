package com.frew.crew.articleCommand;

import com.frew.crew.article.Article;
import com.frew.crew.command.Command;

import java.util.UUID;

public class ArticleCommandMapper {

  // Convert ArticleCommand to ArticleCommandDTO
  public static ArticleCommandDTO toDTO(ArticleCommand articleCommand) {
    if (articleCommand == null) {
      return null;
    }

    ArticleCommandDTO dto = new ArticleCommandDTO();
    dto.setArticleId(articleCommand.getId() != null ? articleCommand.getId().getArticleId() : null);
    dto.setQuantity(articleCommand.getQuantity());
    return dto;
  }

  // Convert ArticleCommandDTO to ArticleCommand
  public static ArticleCommand toEntity(ArticleCommandDTO dto, Command command, Article article) {
    if (dto == null) {
      return null;
    }

    ArticleCommand articleCommand = new ArticleCommand();
    ArticleCommandId articleCommandId = new ArticleCommandId();
    articleCommandId.setArticleId(dto.getArticleId());
    articleCommandId.setCommandId(command.getId()); // Assuming Command has an ID getter

    articleCommand.setId(articleCommandId);
    articleCommand.setCommand(command);
    articleCommand.setArticle(article);
    articleCommand.setQuantity(dto.getQuantity());

    return articleCommand;
  }
}
