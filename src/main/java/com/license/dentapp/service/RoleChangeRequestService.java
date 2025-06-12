package com.license.dentapp.service;

import com.license.dentapp.entity.RoleChangeRequest;
import org.springframework.stereotype.Service;
import com.license.dentapp.dao.RoleChangeRepository;

import java.util.List;

@Service
public class RoleChangeRequestService {

    private final RoleChangeRepository repo;

    public RoleChangeRequestService(RoleChangeRepository repo) {
        this.repo = repo;
    }

    public RoleChangeRequest createRequest(Integer clientId) {
        RoleChangeRequest req = new RoleChangeRequest();
        req.setClientId(clientId);
        req.setStatus(RoleChangeRequest.Status.PENDING);
        return repo.save(req);
    }

    public List<RoleChangeRequest> getByClient(Integer clientId) {
        return repo.findByClientId(clientId);
    }

    public List<RoleChangeRequest> getByStatus(RoleChangeRequest.Status status) {
        return repo.findByStatus(status);
    }

    public RoleChangeRequest updateStatus(Long requestId, RoleChangeRequest.Status newStatus) {
        RoleChangeRequest req = repo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Cerere inexistentă: "+requestId));
        req.setStatus(newStatus);
        return repo.save(req);
    }
}
