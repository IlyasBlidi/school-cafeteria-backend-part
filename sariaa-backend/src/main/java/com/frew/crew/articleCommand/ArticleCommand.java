package com.frew.crew.articleCommand;

import com.frew.crew.article.Article;
import com.frew.crew.command.Command;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(
  name = "articles_commands",
  indexes = {
    @Index(name = "key_article_id", columnList = "article_id"),  // Index for article_id
    @Index(name = "key_command_id", columnList = "command_id"),  // Index for command_id
    @Index(name = "key_article_command", columnList = "article_id, command_id") // Composite index
  }
)
public class ArticleCommand implements Serializable {
  @EmbeddedId
  private ArticleCommandId id;

  @ManyToOne
  @MapsId("commandId")
  @JoinColumn(
    name = "command_id",
    insertable = false,
    updatable = false,
    foreignKey = @ForeignKey(name = "fk_command_id")
  )
  private Command command;

  @ManyToOne
  @MapsId("articleId")
  @JoinColumn(
    name = "article_id",
    insertable = false,
    updatable = false,
    foreignKey = @ForeignKey(name = "fk_article_id")
  )
  private Article article;

  @Column(nullable = false)
  private Integer quantity;
}
