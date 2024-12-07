package com.frew.crew.notification;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser_EmailOrderByCreatedAtDesc(String email);
    List<Notification> findByUser_EmailAndIsReadFalseOrderByCreatedAtDesc(String email);
    long countByUser_EmailAndIsRead(String email, boolean isRead);
}