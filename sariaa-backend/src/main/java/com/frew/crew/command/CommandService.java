package com.frew.crew.command;

import com.frew.crew.article.Article;
import com.frew.crew.article.ArticleRepository;
import com.frew.crew.articleCommand.ArticleCommand;
import com.frew.crew.articleCommand.ArticleCommandDTO;
import com.frew.crew.articleCommand.ArticleCommandId;
import com.frew.crew.articleCommand.ArticleCommandRepository;
import com.frew.crew.card.Card;
import com.frew.crew.card.CardService;
import com.frew.crew.user.User;
import com.frew.crew.user.UserRepository;
import com.frew.crew.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
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
  private final UserService userService;
  private final ArticleRepository articleRepository;

  private final CardService cardService ;

  private final SimpMessagingTemplate messagingTemplate;



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

  public Command getCommandById(UUID commandId){
    Optional<Command> command =  commandRepository.findById(commandId) ;
    return command.get();
  }

  @Transactional
  public Command saveAcceptedCommand(UUID commandId) {

    Command command = getCommandById(commandId) ;
    command.setStatus(Status.ACCEPTED);


    cardService.debitCardBalance(command.getUser().getCard().getId() ,command.getTotalPrice());
    Command savedcommand = commandRepository.save(command) ;
    return savedcommand;
  }

  @Transactional
  public Command saveCookingCommand( UUID commandId) {

    Command command = getCommandById(commandId) ;
    command.setStatus(Status.ACCEPTED);
    Command savedcommand = commandRepository.save(command) ;
    return savedcommand;
  }
  @Transactional
  public Command saveReadyCommand( UUID commandId) {

    Command command = getCommandById(commandId) ;
    command.setStatus(Status.READY);
    Command savedcommand = commandRepository.save(command) ;
    return savedcommand;
  }

  @Transactional
  public Command saveCompletedCommand( UUID commandId) {

    Command command = getCommandById(commandId) ;
    command.setStatus(Status.COMPLETED);
    Command savedcommand = commandRepository.save(command) ;
    return savedcommand;
  }
  @Transactional
  public Command saveDeclinedCommand( UUID commandId) {

    Command command = getCommandById(commandId) ;
    command.setStatus(Status.DECLINED);
    Command savedcommand = commandRepository.save(command) ;
    return savedcommand;
  }



  @Transactional
  public Command saveNewCommand(UUID userId, List<ArticleCommandDTO> articleCommandsDTO) {
    User user = userService.getUser(userId) ;

    Command command = Command.builder()
            .commandDate(LocalDate.now())
            .status(Status.NEW)
            .user(user)
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

      articleCommands.add(articleCommand);
    }

    if (cardService.isAmountAvailable(user.getCard().getId() , this.calculateTotalPrice(articleCommands))){
      for (ArticleCommand articleCommand : articleCommands){

      articlecommandRepository.save(articleCommand);
    }



      savedCommand.setCommandArticles(articleCommands);
      savedCommand.setTotalPrice(this.calculateTotalPrice(articleCommands));
      commandRepository.save(savedCommand) ;
      messagingTemplate.convertAndSend("/topic/NewCommands", command);

      return savedCommand;}
    else
        return Command.builder().status(Status.DECLINED).build() ;
  }
}
