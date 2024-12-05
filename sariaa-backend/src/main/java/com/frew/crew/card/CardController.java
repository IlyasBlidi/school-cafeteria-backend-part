package com.frew.crew.card;

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

  @GetMapping
  public ResponseEntity<List<CardDTO>> getAllCards() {
    List<CardDTO> cards = cardService.getAllCards();
    return ResponseEntity.ok(cards);
  }

  @PatchMapping("/{cardId}")
  public ResponseEntity<Card> chargeCardBalance(@PathVariable UUID cardId, @RequestBody BigDecimal amount) {
    Card card = cardService.chargeCardBalance(cardId, amount);
    return new ResponseEntity<>(card, HttpStatus.ACCEPTED);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<CardBodyDTO> getCardByUserId(@PathVariable String userId) {
    CardBodyDTO card = cardService.findCardByUserId(UUID.fromString(userId));
    return new ResponseEntity<>(card, HttpStatus.OK);
  }
}
