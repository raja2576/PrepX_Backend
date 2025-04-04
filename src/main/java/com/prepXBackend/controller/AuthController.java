package com.prepXBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prepXBackend.config.JwtProvider;
import com.prepXBackend.model.User;
import com.prepXBackend.repository.UserRepository;
import com.prepXBackend.request.LoginRequest;
import com.prepXBackend.response.AuthResponse;
import com.prepXBackend.service.CustomeUserDetailsImpl;
import com.prepXBackend.service.UserService;

//@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomeUserDetailsImpl customeUserDetail;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    // ✅ User Signup
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email already exists with another account");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        String jwtString = jwtProvider.generateToken(savedUser.getEmail());

        AuthResponse response = new AuthResponse(jwtString, "✅ Signup successful", savedUser.getId(),savedUser.getFullName());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

 // ✅ Login
    @PostMapping("/signing")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()); // Get user from DB

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new AuthResponse(null, "❌ Invalid credentials", null,null));
        }

        // ✅ Generate JWT Token
        String token = jwtProvider.generateToken(request.getEmail());

        // ✅ Construct Response with userId
        AuthResponse response = new AuthResponse(token, "✅ Login successful", user.getId(),user.getFullName());

        return ResponseEntity.ok(response);
    }

}
