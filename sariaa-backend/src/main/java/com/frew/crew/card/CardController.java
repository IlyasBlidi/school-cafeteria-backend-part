package com.frew.crew.card;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
