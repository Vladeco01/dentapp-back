package com.license.dentapp.service;
import com.license.dentapp.entity.Notification;
import com.license.dentapp.dao.NotificationRepository;
import com.license.dentapp.dto.NotificationDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationService {
    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public NotificationDto createNotification(Integer userId, String message) {
        Notification n = new Notification(userId, message);
        Notification saved = repo.save(n);
        return mapToDto(saved);
    }

    public List<NotificationDto> getNotificationsForUser(Integer userId) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void markAsRead(Long notificationId) {
        Notification n = repo.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setRead(true);
        repo.save(n);
    }

    private NotificationDto mapToDto(Notification n) {
        return new NotificationDto(
                n.getId(),
                n.getUserId(),
                n.getMessage(),
                n.getCreatedAt(),
                n.isRead()
        );
    }

}
