package com.frew.crew.articleCommand;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public
class ArticleCommandId implements Serializable {
  private UUID articleId;
  private UUID commandId;
}
