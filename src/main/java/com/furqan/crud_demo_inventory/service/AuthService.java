package com.furqan.crud_demo_inventory.service;

import com.furqan.crud_demo_inventory.entity.Role;
import com.furqan.crud_demo_inventory.entity.User;
import com.furqan.crud_demo_inventory.repository.RoleRepository;
import com.furqan.crud_demo_inventory.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Ensure roles list is initialized
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        // Assign default role if none exists
        if (user.getRoles().isEmpty()) {
            Role roleUser = roleRepository.findByRoleName("ROLE_EMPLOYEE")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            user.getRoles().add(roleUser);
        }

        // Keep enabled/verificationToken values from controller
        return userRepository.save(user);
    }
}
