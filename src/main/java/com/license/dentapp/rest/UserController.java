package com.license.dentapp.rest;

import com.license.dentapp.dto.AuthRequest;
import com.license.dentapp.entity.Role;
import com.license.dentapp.entity.User;
import com.license.dentapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(Role.CLIENT);

        User savedUser = userService.register(user);
        return ResponseEntity.ok(savedUser);
    }
}
