package com.license.dentapp.rest;

import com.license.dentapp.entity.RoleChangeRequest;
import com.license.dentapp.service.RoleChangeRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-change-requests")
public class RoleChangeRequestController {

    private final RoleChangeRequestService service;

    public RoleChangeRequestController(RoleChangeRequestService service) {
        this.service = service;
    }

    @PostMapping("/{clientId}")
    public ResponseEntity<RoleChangeRequest> create(
            @PathVariable Integer clientId
    ) {
        RoleChangeRequest created = service.createRequest(clientId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<RoleChangeRequest>> byClient(@PathVariable Integer clientId) {
        return ResponseEntity.ok(service.getByClient(clientId));
    }

    @GetMapping
    public ResponseEntity<List<RoleChangeRequest>> all(
            @RequestParam(required = false) RoleChangeRequest.Status status) {
        if (status != null) {
            return ResponseEntity.ok(service.getByStatus(status));
        }
        return ResponseEntity.ok(service.getByStatus(RoleChangeRequest.Status.PENDING));
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<RoleChangeRequest> updateStatus(
            @PathVariable Long requestId,
            @RequestParam RoleChangeRequest.Status status) {
        RoleChangeRequest updated = service.updateStatus(requestId, status);
        return ResponseEntity.ok(updated);
    }
}
