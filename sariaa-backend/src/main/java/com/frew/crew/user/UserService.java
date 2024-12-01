package com.frew.crew.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public List<UserDTO> getAllUsers() {
    return userRepository.findAll()
            .stream()
            .map(UserMapper::toUserDTO)
            .collect(Collectors.toList());
  }

  public User getUser(UUID userId){
    Optional<User> user = userRepository.findById(userId) ;
    return user.get() ;
  }


  public void deleteUserById(UUID userId) {
    userRepository.deleteById(userId);
  }
}
