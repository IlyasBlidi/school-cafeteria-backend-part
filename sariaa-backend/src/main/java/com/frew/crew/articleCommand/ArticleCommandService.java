package com.frew.crew.articleCommand;

import com.frew.crew.article.Article;
import com.frew.crew.article.ArticleDTO;
import com.frew.crew.article.ArticleMapper;
import com.frew.crew.article.ArticleRepository;
import com.frew.crew.command.Command;
import com.frew.crew.command.CommandDetailsDTO;
import com.frew.crew.command.CommandMapper;
import com.frew.crew.command.CommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleCommandService {
  private final ArticleCommandRepository articleCommandRepository;
  private final ArticleRepository articleRepository;
  private final CommandRepository commandRepository;

  public List<ArticleCommandDTO> getAllArticleCommands() {
    return articleCommandRepository.findAll()
      .stream()
      .map(ArticleCommandMapper::toArticleCommandDTO)
      .collect(Collectors.toList());
  }

  private void addArticleToCommand(ArticleCommand articleCommandDetails) {
    UUID articleId = articleCommandDetails.getId().getArticleId();
    UUID commandId = articleCommandDetails.getId().getCommandId();

    Article article = articleRepository.findById(articleId).orElseThrow();
    Command command = commandRepository.findById(commandId).orElseThrow();

    article.setMaxQuantityAvailable(
      article.getMaxQuantityAvailable() - articleCommandDetails.getQuantity()
    );

    articleCommandDetails.setArticle(article);
    articleCommandDetails.setCommand(command);
    articleCommandDetails.setId(new ArticleCommandId(articleId, commandId));

    articleRepository.save(article);
    articleCommandRepository.save(articleCommandDetails);
  }

  public CommandDetailsDTO constructUserCommand(
    UUID commandId,
    Map<UUID, Integer> articlesQuantities
  ) {
    // Find the command by ID
    Optional<Command> commandOpt = commandRepository.findById(commandId);
    if (commandOpt.isEmpty()) {
      throw new RuntimeException("Command not found for ID: " + commandId);
    }

    Command command = commandOpt.get();

    // Collect all article IDs from the map
    List<UUID> articlesIds = new LinkedList<>(articlesQuantities.keySet());

    // Fetch all articles in a single query
    List<Article> articles = articleRepository.findAllById(articlesIds);

    // Map articles by their ID for faster lookup
    Map<UUID, Article> articleMap = articles
      .stream()
      .collect(Collectors.toMap(Article::getId, article -> article));

    // List to hold ArticleDTO objects
    List<ArticleDTO> articleDTOs = new LinkedList<>();

    // Calculate the total price and create ArticleCommand entities
    List<BigDecimal> totalPrice = new LinkedList<>();

    articlesQuantities.forEach((articleId, quantity) -> {
      // Ensure the quantity is valid
      if (quantity <= 0) {
        throw new RuntimeException("Invalid quantity for article ID: " + articleId);
      }

      // Get the article by its ID
      Article article = articleMap.get(articleId);
      if (article == null) {
        throw new RuntimeException("Article not found for ID: " + articleId);
      }

      // Create ArticleCommandId
      ArticleCommandId articleCommandId = new ArticleCommandId(article.getId(), commandId);

      // Create ArticleCommand object
      ArticleCommand articleCommand = new ArticleCommand();
      articleCommand.setId(articleCommandId);
      articleCommand.setQuantity(quantity);
      articleCommand.setArticle(article);
      articleCommand.setCommand(command);

      // Save the ArticleCommand to the database (if needed)
      articleCommandRepository.save(articleCommand);

      // Update the total price
      totalPrice.add(article.getPrice().multiply(BigDecimal.valueOf(quantity)));

      // Create ArticleDTO and add it to the list
      ArticleDTO articleDTO = ArticleMapper.toArticleDTO(article, quantity);
      articleDTOs.add(articleDTO);
    });

    // Set the total price in the command
    command.setTotalPrice(totalPrice.stream().reduce(BigDecimal.ZERO, BigDecimal::add));

    // Save the updated command
    commandRepository.save(command);

    // Return the DTO with updated details
    return new CommandDetailsDTO(
      commandId,
      command.getTotalPrice(),
      command.getCommandDate(),
      CommandMapper.toCommandDTO(command).getUser(),
      articleDTOs // Include the list of ArticleDTOs
    );
  }
}
