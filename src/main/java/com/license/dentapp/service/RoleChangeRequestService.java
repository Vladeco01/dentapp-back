package com.license.dentapp.service;

import com.license.dentapp.dao.UserRepository;
import com.license.dentapp.entity.Role;
import com.license.dentapp.entity.RoleChangeRequest;
import com.license.dentapp.entity.User;
import org.springframework.stereotype.Service;
import com.license.dentapp.dao.RoleChangeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleChangeRequestService {

    private final UserRepository userRepository;
    private final RoleChangeRepository repo;

    public RoleChangeRequestService(RoleChangeRepository repo, UserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
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

        Optional<User> optionalUser = userRepository.findById(req.getClientId());
        User user = optionalUser.get();
        if(newStatus == RoleChangeRequest.Status.ACCEPTED){
            user.setRole(Role.DENTIST);
        }
        userRepository.save(user);
        return repo.save(req);
    }
}
