package com.frew.crew.notification;

import com.frew.crew.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationMapper notificationMapper = new NotificationMapper();

    public Notification createNotification(User user, NotificationType type, String title,
                                           String message) {

        Notification notification = Notification.builder()
                .user(user)
                .type(type)
                .title(title)
                .message(message)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        notification = notificationRepository.save(notification);
        sendRealTimeNotification(notification);
        return notification;
    }

    private void sendRealTimeNotification(Notification notification) {
        NotificationDTO dto = notificationMapper.toDTO(notification);
        messagingTemplate.convertAndSendToUser(
                notification.getUser().getEmail(),
                "/queue/notifications",
                dto
        );
    }

    public void sendBalanceNotification(User user, BigDecimal amount) {
        Notification notification = new Notification();
        notification.setTitle("Balance Update");
        notification.setMessage(String.format("Your card has been charged with %.2f MAD", amount));
        notification.setType(NotificationType.SYSTEM_NOTIFICATION);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUser(user);


        String destination = String.format("/user/%s/queue/notifications", user.getEmail());
        messagingTemplate.convertAndSend(destination, notificationMapper.toDTO(notification));
    }
}