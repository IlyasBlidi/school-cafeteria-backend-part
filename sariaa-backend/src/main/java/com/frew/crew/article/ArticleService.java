package com.frew.crew.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;

  public void saveArticle(Article article) {
    articleRepository.save(article);
  }

  public List<Article> getAllArticles() {
    return articleRepository.findAll();
  }
}
