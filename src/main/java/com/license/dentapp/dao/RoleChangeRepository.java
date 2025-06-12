package com.license.dentapp.dao;

import com.license.dentapp.entity.RoleChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleChangeRepository extends JpaRepository<RoleChangeRequest, Long> {
    List<RoleChangeRequest> findByClientId(Integer clientId);
    List<RoleChangeRequest> findByStatus(RoleChangeRequest.Status status);
}