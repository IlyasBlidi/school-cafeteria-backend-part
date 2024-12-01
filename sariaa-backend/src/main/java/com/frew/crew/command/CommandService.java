package com.frew.crew.command;

import com.frew.crew.articleCommand.ArticleCommand;
import com.frew.crew.user.User;
import com.frew.crew.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandService {
  private final CommandRepository commandRepository;
  private final UserRepository userRepository;

  private BigDecimal calculateTotalPrice(List<ArticleCommand> articles) {
    BigDecimal totalPrice = BigDecimal.ZERO;
    for (ArticleCommand article : articles) {
      totalPrice = totalPrice.add(
        article.getArticle().getPrice().multiply(BigDecimal.valueOf(article.getQuantity()))
      );
    }
    return totalPrice;
  }

  public List<CommandDTO> getAllCommands() {
    return commandRepository.findAll()
      .stream()
      .map(CommandMapper::toCommandDTO)
      .collect(Collectors.toList());
  }

  public Command saveCommand(UUID userId) {
    Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }
    Command command = Command.builder()
      .totalPrice(BigDecimal.ZERO)
      .commandDate(LocalDate.now())
      .user(user.get())
      .build();

    return commandRepository.save(command);
  }
}
