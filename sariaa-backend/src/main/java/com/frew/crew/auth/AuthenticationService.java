package com.frew.crew.auth;

import com.frew.crew.card.Card;
import com.frew.crew.card.CardRepository;
import com.frew.crew.config.JwtService;
import com.frew.crew.role.Role;
import com.frew.crew.user.User;
import com.frew.crew.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final CardRepository cardRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    // Create a new Card for the user
    Card card = Card.builder()
        .balance(BigDecimal.ZERO) // Default initial balance
        .lastUpdateDate(LocalDate.now())
        .build();

    cardRepository.save(card);

    // Create the User instance and associate the card
    var user = User.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole() != null ? request.getRole() : Role.USER)
        .card(card) // Associate the card
        .build();

    // Synchronize the bidirectional relationship

    // Save the user (and the card due to CascadeType.ALL)
    repository.save(user);

    // Generate JWT token
    var jwtToken = jwtService.generateToken(user);

    return AuthenticationResponse.builder()
      .identifier(user.getId())
      .firstName(user.getFirstName())
      .lastName(user.getLastName())
      .email(user.getEmail())
      .role(user.getRole())
      .token(jwtToken)
      .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
      .identifier(user.getId())
      .firstName(user.getFirstName())
      .lastName(user.getLastName())
      .email(user.getEmail())
      .role(user.getRole())
      .card(user.getCard())
      .token(jwtToken)
      .build();
  }
}
