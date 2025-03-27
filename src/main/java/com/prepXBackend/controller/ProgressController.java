package com.prepXBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prepXBackend.config.JwtProvider;
import com.prepXBackend.model.Progress;
import com.prepXBackend.model.User;
import com.prepXBackend.request.ProgressRequest;
import com.prepXBackend.service.ProgressService;
import com.prepXBackend.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider; // ✅ Inject JwtProvider instead of calling it statically

    // ✅ Save User Progress (Fixes previous issues)
    @PostMapping("/save")
    public ResponseEntity<String> saveProgress(
            @RequestBody ProgressRequest request,
            @RequestHeader("Authorization") String token) throws Exception {

        // Strip "Bearer " prefix if it exists
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        // Validate the token using instance method
        if (!jwtProvider.validateToken(jwtToken)) {  // ✅ Use instance method
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Invalid or expired token.");
        }

        // Extract the email from the token using instance method
        String email = jwtProvider.getEmailFromToken(jwtToken); // ✅ Use instance method
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: User not found.");
        }

        // Proceed with saving the progress
        Optional<User> userOptional = userService.getUserByEmail(email);  // Retrieve the user based on email
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: User not found.");
        }

        User user = userOptional.get();
        
        // Create and save the progress
        Progress progress = new Progress();
        progress.setUser(user);
        progress.setUsername(user.getFullName());
        progress.setScore(request.getScore());
        progress.setTotalQuestions(request.getTotalQuestions());
        progress.setCategory(request.getCategory());
        progress.setDifficulty(request.getDifficulty());
        progress.setDate(LocalDateTime.now());

        progressService.saveProgress(progress);
        return ResponseEntity.ok("Progress saved successfully!");
    }

    // ✅ Get Progress for a Specific User (Includes username & timestamp)
    @GetMapping("/{userId}")
    public ResponseEntity<List<Progress>> getProgress(@PathVariable(required = false) Long userId) {
        System.out.println("Received userId: " + userId);  // Debugging

        if (userId == null) {
            return ResponseEntity.badRequest().body(null);  // Prevent conversion errors
        }

        List<Progress> progressList = progressService.getUserProgress(userId);
        return progressList.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                                      : ResponseEntity.ok(progressList);
    }
}
