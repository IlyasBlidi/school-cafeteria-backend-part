package com.frew.crew.notification;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
public class NotificationMapper {
    public NotificationDTO toDTO(Notification notification) {
        String timeAgo = formatTimeAgo(notification.getCreatedAt());

        return NotificationDTO.builder()
                .id(notification.getId())
                .type(notification.getType() != null ? notification.getType().name() : null)
                .title(notification.getTitle())
                .message(notification.getMessage())
                .userName(notification.getUser().getFirstName() + " " + notification.getUser().getLastName())
                .timeAgo(timeAgo)
                .isRead(notification.isRead())
                .target(getTargetForType(notification.getType()))
                .actions(getActionsForType(notification.getType()))
                .build();
    }

    private String formatTimeAgo(LocalDateTime dateTime) {
        if (dateTime == null) return null;

        Duration duration = Duration.between(dateTime, LocalDateTime.now());
        long minutes = duration.toMinutes();

        if (minutes < 1) return "just now";
        if (minutes < 60) return minutes + " minutes ago";

        long hours = duration.toHours();
        if (hours < 24) return hours + " hours ago";

        long days = duration.toDays();
        if (days < 30) return days + " days ago";

        return dateTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy"));
    }

    private String getTargetForType(NotificationType type) {
        if (type == null) return null;

        return switch (type) {
            case ORDER_PLACED, ORDER_READY, ORDER_CONFIRMED, ORDER_COMPLETED -> "Order Details";
            case BALANCE_UPDATED, LOW_BALANCE -> "Account Balance";
            case SYSTEM_NOTIFICATION -> "System";
            default -> null;
        };
    }

    private List<String> getActionsForType(NotificationType type) {
        if (type == null) return List.of();

        return switch (type) {
            case ORDER_READY -> List.of("View Order", "Get Directions");
            case LOW_BALANCE -> List.of("Top Up", "View Balance");
            case ORDER_PLACED -> List.of("Track Order");
            default -> List.of();
        };
    }
}