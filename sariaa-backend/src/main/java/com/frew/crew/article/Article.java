package com.frew.crew.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frew.crew.articleCommand.ArticleCommand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "articles")
public class Article {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private BigDecimal price;
  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<ArticleCommand> articleCommands;
}
