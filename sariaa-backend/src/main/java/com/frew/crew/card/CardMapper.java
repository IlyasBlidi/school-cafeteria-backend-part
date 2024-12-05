package com.frew.crew.card;

import static com.frew.crew.user.UserMapper.toUserSummaryDTO;

public class CardMapper {
  private CardMapper() {
    // Prevent instantiation
  }

  public static CardDTO toCardDTO(Card card) {
    return new CardDTO(
      card.getId(),
      card.getBalance(),
      card.getLastUpdateDate(),
      card.getUser() != null ? toUserSummaryDTO(card.getUser()) : null
    );
  }

  public static CardBodyDTO toCardBodyDTO(Card card) {
    return CardBodyDTO.builder()
      .cardId(card.getId())
      .balance(card.getBalance())
      .lastUpdateDate(card.getLastUpdateDate())
      .build();
  }
}
