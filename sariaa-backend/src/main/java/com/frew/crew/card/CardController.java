package com.frew.crew.card;

import com.frew.crew.notification.NotificationService;
import com.frew.crew.user.User;
import com.frew.crew.user.UserRepository;
import com.frew.crew.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {
  private final CardService cardService;
  private final UserRepository userRepository;
  private final UserService userService;
  private final NotificationService notificationService;

  @GetMapping
  public ResponseEntity<List<CardDTO>> getAllCards() {
    List<CardDTO> cards = cardService.getAllCards();
    return ResponseEntity.ok(cards);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<CardBodyDTO> getCardByUserId(@PathVariable String userId) {
    CardBodyDTO card = cardService.findCardByUserId(UUID.fromString(userId));
    return new ResponseEntity<>(card, HttpStatus.OK);
  }

  @PatchMapping("/{cardId}")
  public ResponseEntity<CardBodyDTO> chargeCardBalance(@PathVariable UUID cardId, @RequestBody BigDecimal amount) {
    CardBodyDTO card = cardService.chargeCardBalance(cardId, amount);

    User user = (User) userRepository.findByCardId(cardId)
            .orElseThrow(() -> new RuntimeException("User not found for card"));

    notificationService.sendBalanceNotification(user, amount);

    return new ResponseEntity<>(card, HttpStatus.ACCEPTED);
  }

}
