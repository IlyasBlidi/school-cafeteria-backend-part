package com.frew.crew.card;

import static com.frew.crew.user.UserMapper.toUserSummaryDTO;

public class CardMapper {
  public static CardDTO toCardDTO(Card card) {
    return new CardDTO(
        card.getId(),
        card.getBalance(),
        card.getLastUpdateDate(),
        card.getUser() != null ? toUserSummaryDTO(card.getUser()) : null
    );
  }
}
