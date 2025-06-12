package com.license.dentapp.rest;

import com.license.dentapp.dto.NotificationDto;
import com.license.dentapp.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<NotificationDto>> getForUser(@RequestParam Integer userId) {
        List<NotificationDto> list = service.getNotificationsForUser(userId);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        service.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}