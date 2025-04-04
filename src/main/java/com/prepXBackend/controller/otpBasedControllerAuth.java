package com.prepXBackend.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.prepXBackend.config.JwtProvider;
import com.prepXBackend.model.User;
import com.prepXBackend.repository.UserRepository;
import com.prepXBackend.request.LoginRequest;
import com.prepXBackend.request.OtpRequest;
import com.prepXBackend.response.AuthResponse;
import com.prepXBackend.service.EmailService;
import com.prepXBackend.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend access
public class otpBasedControllerAuth {

	

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private EmailService emailService;

    private Map<String, String> otpStore = new HashMap<>(); // Store OTPs temporarily

    // ✅ User Signup
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) throws Exception {
        // Check if user already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("❌ Email already exists!");
        }

        // Save user details
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        // Generate JWT
        String jwtString = jwtProvider.generateToken(savedUser.getEmail());

        AuthResponse response = new AuthResponse(jwtString, "✅ Registration successful", savedUser.getId(), savedUser.getFullName());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    // ✅ Step 1: Send OTP to Email
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ User not found");
        }

        // Generate 6-digit OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStore.put(request.getEmail(), otp); // Store OTP

        // Send OTP via Email
        emailService.sendEmail(request.getEmail(), "Your OTP Code", "Your OTP is: " + otp);

        return ResponseEntity.ok("✅ OTP sent successfully");
    }

    // ✅ Step 2: Verify OTP and Login
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest otpRequest) {
        String storedOtp = otpStore.get(otpRequest.getEmail());

        if (storedOtp == null || !storedOtp.equals(otpRequest.getOtp())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, "❌ Invalid OTP", null, null));
        }

        User user = userRepository.findByEmail(otpRequest.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ User not found");
        }

        // ✅ Generate JWT Token after OTP verification
        String token = jwtProvider.generateToken(user.getEmail());

        // Remove OTP from store
        otpStore.remove(otpRequest.getEmail());

        // ✅ Construct Response with userId
        AuthResponse response = new AuthResponse(token, "✅ Login successful", user.getId(), user.getFullName());

        return ResponseEntity.ok(response);
    }
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    
    @PostMapping("/send-otp-register")
    public ResponseEntity<String> sendOtpForRegistration(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // 🔹 Check if user already exists in the database
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Email already exists! Please login instead.");
        }

        // 🔹 Generate OTP
        String otp = generateOtp();
        otpStorage.put(email, otp); // ✅ Fix: Use otpStorage correctly

        // 🔹 Send OTP via email
        emailService.sendOtp(email, otp); // ✅ Ensure `emailService` is working

        return ResponseEntity.ok("✅ OTP sent successfully for registration!");
    }
    
    
    @PostMapping("/verify-otp-register")
    public ResponseEntity<Boolean> verifyOtpForRegistration(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String enteredOtp = request.get("otp");

        // 🔹 Check if OTP is correct
        String storedOtp = otpStorage.get(email);
        if (storedOtp != null && storedOtp.equals(enteredOtp)) {
            otpStorage.remove(email); // Remove OTP after successful verification
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
    

    private String generateOtp() {
        int otp = (int) (Math.random() * 900000) + 100000; // Generates a 6-digit OTP
        return String.valueOf(otp);
    }

	
}
