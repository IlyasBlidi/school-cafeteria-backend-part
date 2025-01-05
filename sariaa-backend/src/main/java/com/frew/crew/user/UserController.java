package com.frew.crew.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Component
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService userService;
  private  final UserRepository userRepository;

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    List<UserDTO> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<User> getUser(@PathVariable UUID userId) {
    User user = userService.getUser(userId);
    return ResponseEntity.ok(user);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<User> deleteUserById(@PathVariable UUID userId) {
    userService.deleteUserById(userId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/generate")
  public void generate(){
    List<User> users = UserDataGenerator.generateUsers(1000000);

    users.forEach(user -> {
      user.setPassword(user.getPassword());
      userRepository.save(user);
    });
  }
}
