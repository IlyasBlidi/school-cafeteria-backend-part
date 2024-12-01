package com.frew.crew.command;

import com.frew.crew.article.Article;
import com.frew.crew.article.ArticleRepository;
import com.frew.crew.articleCommand.ArticleCommand;
import com.frew.crew.articleCommand.ArticleCommandDTO;
import com.frew.crew.articleCommand.ArticleCommandId;
import com.frew.crew.articleCommand.ArticleCommandRepository;
import com.frew.crew.user.User;
import com.frew.crew.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommandService {
  private final CommandRepository commandRepository;
  private final ArticleCommandRepository articlecommandRepository;
  private final UserRepository userRepository;
  private final ArticleRepository articleRepository;

  private BigDecimal calculateTotalPrice(List<ArticleCommand> articles) {
    BigDecimal totalPrice = BigDecimal.ZERO;
    for (ArticleCommand article : articles) {
      totalPrice = totalPrice.add(
        article.getArticle().getPrice().multiply(BigDecimal.valueOf(article.getQuantity()))
      );
    }
    return totalPrice;
  }

  public List<Command> getAllCommands() {
    return commandRepository.findAll();
  }

  @Transactional
  public Command saveCommand(UUID userId, List<ArticleCommandDTO> articleCommandsDTO) {
    Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }

    Command command = Command.builder()
            .commandDate(LocalDate.now())
            .user(user.get())
            .build();

    // First, save the command to generate its ID
    Command savedCommand = commandRepository.save(command);

    List<ArticleCommand> articleCommands = new ArrayList<>();

    for (ArticleCommandDTO articleCommandDto : articleCommandsDTO) {
      Optional<Article> article = articleRepository.findById(articleCommandDto.getArticleId());
      if (article.isEmpty()) {
        throw new EntityNotFoundException("Article not found with ID: " + articleCommandDto.getArticleId());
      }

      ArticleCommandId articleCommandId = ArticleCommandId
              .builder()
              .articleId(article.get().getId())
              .commandId(savedCommand.getId())
              .build();

      ArticleCommand articleCommand = ArticleCommand
              .builder()
              .id(articleCommandId)
              .command(savedCommand)
              .article(article.get())
              .quantity(articleCommandDto.getQuantity())
              .build();

      articlecommandRepository.save(articleCommand);
      articleCommands.add(articleCommand);
    }


    savedCommand.setCommandArticles(articleCommands);
    savedCommand.setTotalPrice(this.calculateTotalPrice(articleCommands));

    commandRepository.save(savedCommand) ;


    return savedCommand;
  }
}
