package com.frew.crew.article;

public class ArticleMapper {
  public static ArticleDTO toArticleDTO(Article article, Integer quantity) {
    return new ArticleDTO(
      article.getId(),
      article.getTitle(),
      article.getDescription(),
      article.getPrice(),
      quantity
    );
  }
}
