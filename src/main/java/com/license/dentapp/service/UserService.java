package com.license.dentapp.service;

import com.license.dentapp.dao.UserRepository;
import com.license.dentapp.dto.UserUpdateRequest;
import com.license.dentapp.entity.Role;
import com.license.dentapp.entity.User;
import com.license.dentapp.utils.CustomUserDetails;
import com.license.dentapp.utils.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User findById(int theId) {
        return userRepository.findById(theId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public User findByName(String theFirstName) {
        return userRepository.findByFirstName(theFirstName)
                .orElseThrow(() -> new RuntimeException("User not found by name"));
    }


    public User findByEmail(String theMail) {
        return userRepository.findByEmail(theMail)
                .orElseThrow(() -> new RuntimeException("User not found by email"));
    }


    public User save(User theUser) {
        if (theUser.getId() == null) {
            theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));
        }
        return userRepository.save(theUser);
    }


    public void deleteById(int theId) {
        if (!userRepository.existsById(theId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(theId);
    }


    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email deja folosit");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email invalid"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Parolă invalidă");
        }

        return jwtUtil.generateToken(new CustomUserDetails(user));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Integer id, UserUpdateRequest dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User nu a fost gasit: " + id));

        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        return userRepository.save(user);
    }

    public List<User> getAllDentists() {
        return userRepository.findByRole(Role.DENTIST);
    }

}
