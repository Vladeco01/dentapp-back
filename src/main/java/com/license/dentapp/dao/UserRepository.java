package com.license.dentapp.dao;

import com.license.dentapp.entity.Role;
import com.license.dentapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByFirstName(String firstName);

    List<User> findByClinicId(int clinicId);
    List<User> findByRole(Role role);
}
