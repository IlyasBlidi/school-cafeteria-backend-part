package com.frew.crew.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frew.crew.articleCommand.ArticleCommand;
import com.frew.crew.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
  name = "commands",
  indexes = @Index(name = "key_user_id", columnList = "user_id")
)
public class Command {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(nullable = false)
  private BigDecimal totalPrice;

  @Column(nullable = false)
  private LocalDate commandDate;

  @OneToMany(mappedBy = "command", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<ArticleCommand> commandArticles;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(
    name = "user_id",
    foreignKey = @ForeignKey(name = "fk_user_id")
  )
  private User user;
}
