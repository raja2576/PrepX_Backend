package com.prepXBackend.service;

import com.prepXBackend.model.User;
import com.prepXBackend.repository.UserRepository;
import com.prepXBackend.config.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        if (!jwtProvider.validateToken(jwt)) {
            throw new Exception("Invalid or expired token.");
        }

        String email = jwtProvider.getEmailFromToken(jwt);
        if (email == null || email.isEmpty()) {
            throw new Exception("User not found from the token.");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found for email: " + email);
        }

        return user;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {  
        return Optional.ofNullable(userRepository.findByEmail(email));  // ✅ Ensure this returns Optional<User>
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found for ID: " + userId));
    }


    @Override
    public User getUserFromToken(String token) {
        if (!jwtProvider.validateToken(token)) {
            logger.error("Invalid or expired token provided.");
            throw new RuntimeException("Invalid or expired token.");
        }

        String email = jwtProvider.getEmailFromToken(token);
        if (email == null || email.isEmpty()) {
            logger.error("Failed to extract email from token.");
            throw new RuntimeException("Could not extract email from token.");
        }

        logger.info("Extracted email from token: {}", email);

        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.error("No user found with email: {}", email);
            throw new RuntimeException("User not found for email: " + email);
        }

        return user;
    }
}
