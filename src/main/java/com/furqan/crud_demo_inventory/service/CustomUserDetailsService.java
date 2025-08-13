package com.furqan.crud_demo_inventory.service;

import com.furqan.crud_demo_inventory.entity.User;
import com.furqan.crud_demo_inventory.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No user found with email: " + email
                ));

        // Return Spring Security user with email as username
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())   // this is what Spring will call "username"
                .password(user.getPassword())    // must already be BCrypt encoded
                .authorities(
                        user.getRoles()
                                .stream()
                                .map(role -> role.getRoleName()) // e.g., ROLE_ADMIN
                                .toArray(String[]::new)
                )
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isEnabled()) // if enabled == false, user can't login
                .build();
    }
}
