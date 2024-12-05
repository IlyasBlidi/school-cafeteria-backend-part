package com.frew.crew.notification;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.frew.crew.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationMapper notificationMapper = new NotificationMapper();

    public Notification createNotification(User user, NotificationType type, String title,
                                           String message, Object metadata) {

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

}