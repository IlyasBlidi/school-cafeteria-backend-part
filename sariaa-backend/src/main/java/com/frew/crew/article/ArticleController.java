package com.frew.crew.article;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {
  private final ArticleService articleService;

  @GetMapping
  public ResponseEntity<List<Article>> getAllArticles() {
    List<Article> articles = articleService.getAllArticles();
    return new ResponseEntity<>(articles, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Article> createArticle(@RequestBody Article article) {
    articleService.saveArticle(article);
    return new ResponseEntity<>(article, HttpStatus.CREATED);
  }
}
