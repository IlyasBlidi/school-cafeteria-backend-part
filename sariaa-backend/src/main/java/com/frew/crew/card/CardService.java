package com.frew.crew.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
