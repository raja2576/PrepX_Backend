package com.prepXBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prepXBackend.model.User;
import com.prepXBackend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomeUserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch the user by email
        User user = userRepository.findByEmail(email); // Using email as username

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Authorities list (you can add roles here later if needed)
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Return a UserDetails object (this is used by Spring Security)
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), authorities);
    }
}
