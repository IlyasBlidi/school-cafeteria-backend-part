package com.frew.crew.card;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {
  private final CardRepository cardRepository;

  public List<CardDTO> getAllCards() {
    return cardRepository.findAll()
        .stream()
        .map(CardMapper::toCardDTO)
        .collect(Collectors.toList());
  }

  public Card chargeCardBalance(UUID cardId, BigDecimal amount) {
    Optional<Card> card = cardRepository.findById(cardId);
    if (card.isEmpty()) {
      throw new RuntimeException("Card not found");
    }
    Card cardToCharge = card.get();
    cardToCharge.setBalance(cardToCharge.getBalance().add(amount));
    cardToCharge.setLastUpdateDate(LocalDate.now());
    return cardRepository.save(cardToCharge);
  }

  @Transactional
  public Card debitCardBalance(UUID cardId , BigDecimal amount) {
    Optional<Card> card = cardRepository.findById(cardId);
    if (card.isEmpty()) {
      throw new RuntimeException("Card not found");
    }

    Card cardTodebit = card.get();
    if(cardTodebit.getBalance().compareTo(amount) < 0) {
      throw new RuntimeException("solde insuffisant");
    }else{
    cardTodebit.setBalance(cardTodebit.getBalance().subtract(amount));
    cardTodebit.setLastUpdateDate(LocalDate.now());
    }

    return cardRepository.save(cardTodebit);

  }
}
