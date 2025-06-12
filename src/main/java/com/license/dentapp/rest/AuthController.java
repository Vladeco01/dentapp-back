package com.license.dentapp.rest;

import com.license.dentapp.dao.UserRepository;
import com.license.dentapp.dto.AuthRequest;
import com.license.dentapp.dto.AuthResponse;
import com.license.dentapp.entity.Role;
import com.license.dentapp.entity.User;
import com.license.dentapp.service.CustomUserDetailsService;
import com.license.dentapp.service.UserService;
import com.license.dentapp.utils.CustomUserDetails;
import com.license.dentapp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        String jwt = jwtUtil.generateToken(userDetails);
        Optional<User> userOpt = userRepository.findByEmail(authRequest.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        User user = userOpt.get();
        Integer clientId = user.getId();
        String role = user.getRole().toString();

        return ResponseEntity.ok(new AuthResponse(jwt,clientId,role));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        User user = new User();
        user.setEmail(authRequest.getEmail());
        user.setPassword(authRequest.getPassword());
        user.setFirstName(authRequest.getFirstName());
        user.setLastName(authRequest.getLastName());
        user.setRole(Role.CLIENT);


        User savedUser = userService.register(user);


        String jwt = jwtUtil.generateToken(new CustomUserDetails(savedUser));

        return ResponseEntity.ok(new AuthResponse(jwt,user.getId(),user.getRole().toString()));
    }

    private boolean isPasswordStrong(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return password != null && password.matches(passwordPattern);
    }


}
