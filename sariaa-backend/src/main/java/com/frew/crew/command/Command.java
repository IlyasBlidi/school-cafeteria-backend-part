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
@Table(name = "commands")
public class Command {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column
  private BigDecimal totalPrice;

  @Column(nullable = false)
  private LocalDate commandDate;

  @Column(name = "status")
  private Status status;

  @OneToMany(mappedBy = "command", cascade = CascadeType.ALL, orphanRemoval = true)

  private List<ArticleCommand> commandArticles;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
