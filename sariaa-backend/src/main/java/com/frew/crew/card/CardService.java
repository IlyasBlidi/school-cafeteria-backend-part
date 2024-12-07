package com.frew.crew.card;


import jakarta.transaction.Transactional;
import com.frew.crew.user.User;
import com.frew.crew.user.UserRepository;
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
  private final UserRepository userRepository;

  public List<CardDTO> getAllCards() {
    return cardRepository.findAll()
        .stream()
        .map(CardMapper::toCardDTO)
        .collect(Collectors.toList());
  }

  public CardBodyDTO chargeCardBalance(UUID cardId, BigDecimal amount) {
    Optional<Card> card = cardRepository.findById(cardId);
    if (card.isEmpty()) {
      throw new RuntimeException("Card not found");
    }
    Card cardToCharge = card.get();
    cardToCharge.setBalance(cardToCharge.getBalance().add(amount));
    cardToCharge.setLastUpdateDate(LocalDate.now());
    cardRepository.save(cardToCharge);
    return CardMapper.toCardBodyDTO(cardToCharge);
  }

  public CardBodyDTO findCardByUserId(UUID userId) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new RuntimeException("User not found");
    }
    return CardMapper.toCardBodyDTO(cardRepository.findByUserId(userId));
  }

  @Transactional
  public Card debitCardBalance(UUID cardId , BigDecimal amount) {
    Optional<Card> card = cardRepository.findById(cardId);
    if (card.isEmpty()) {
      throw new RuntimeException("Card not found");
    }

    Card cardTodebit = card.get();
    if(!isAmountAvailable(cardId , amount)) {
      throw new RuntimeException("solde insuffisant");
    }else{
    cardTodebit.setBalance(cardTodebit.getBalance().subtract(amount));
    cardTodebit.setLastUpdateDate(LocalDate.now());
    }

    return cardRepository.save(cardTodebit);

  }

  public boolean isAmountAvailable(UUID cardId , BigDecimal amount){

    if(getCardById( cardId).getBalance().compareTo(amount) < 0) {
      return false ;
    }else return true ;

  }

  public Card getCardById(UUID cardId){
    Optional<Card> card = cardRepository.findById(cardId);
    if (card.isEmpty()) {
      throw new RuntimeException("Card not found");
    }

   return card.get();
  }
}
