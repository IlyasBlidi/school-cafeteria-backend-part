package com.frew.crew.notification;

import com.frew.crew.user.User;
import com.frew.crew.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;


    @GetMapping
    public List<NotificationDTO> getUserNotifications() {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        return notificationRepository
                .findByUser_EmailOrderByCreatedAtDesc(email)
                .stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
    }


    @PostMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        notificationRepository.findById(id)
                .filter(notification -> notification.getUser().getEmail().equals(email))
                .ifPresent(notification -> {
                    notification.setRead(true);
                    notificationRepository.save(notification);

                    String destination = String.format("/user/%s/queue/notifications", email);
                    messagingTemplate.convertAndSend(destination, notificationMapper.toDTO(notification));
                });
    }

    @PostMapping("/read-all")
    public void markAllAsRead(@AuthenticationPrincipal User userDetails) {
        if (userDetails == null) {
            throw new IllegalStateException("User is not authenticated");
        }
        String email = userDetails.getUsername();
        List<Notification> unreadNotifications = notificationRepository
                .findByUser_EmailAndIsReadFalseOrderByCreatedAtDesc(email);
        unreadNotifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }

    @PostMapping("/add/{user_id}")
    public ResponseEntity<String> addNotification(@RequestBody Notification request,
                                                  @PathVariable(name = "user_id") UUID userId) {
        Notification notification = new Notification();
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setType(NotificationType.SYSTEM_NOTIFICATION);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
        notificationRepository.save(notification);

        String destination = String.format("/user/%s/queue/notifications", notification.getUser().getEmail());
        messagingTemplate.convertAndSend(destination, notificationMapper.toDTO(notification));

        return ResponseEntity.ok("Notification added successfully");
    }
}
