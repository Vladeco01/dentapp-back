package com.license.dentapp.rest;

import com.license.dentapp.dao.UserRepository;
import com.license.dentapp.dto.UserUpdateRequest;
import com.license.dentapp.entity.User;
import com.license.dentapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return ResponseEntity.of(userOpt);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User user) {
        User updated = userService.save(user);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<User> update(
            @PathVariable Integer id,
            @RequestBody UserUpdateRequest dto
    ) {
        User updated = userService.updateUser(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dentists")
    public ResponseEntity<List<User>> getAllDentists() {
        List<User> dentists = userService.getAllDentists();
        return ResponseEntity.ok(dentists);
    }
}
