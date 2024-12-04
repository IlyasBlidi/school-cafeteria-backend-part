package com.frew.crew.articleCommand;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frew.crew.article.Article;
import com.frew.crew.command.Command;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "articles_commands")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleCommand implements Serializable {
  @EmbeddedId
  private ArticleCommandId id;

  @ManyToOne
  @MapsId("commandId")
  @JoinColumn(name = "command_id", insertable = false, updatable = false)
  @JsonIgnore
  private Command command;

  @ManyToOne
  @MapsId("articleId")
  @JoinColumn(name = "article_id", insertable = false, updatable = false)
  private Article article;

  @Column(nullable = false)
  private Integer quantity;
}
