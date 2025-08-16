package com.furqan.crud_demo_inventory.service;

import com.furqan.crud_demo_inventory.entity.User;
import com.furqan.crud_demo_inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Handle forgot password request
    public void processForgotPassword(String email, String siteURL) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Generate unique reset token
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepository.save(user);

        // Send reset password email
        emailService.sendPasswordResetEmail(user, siteURL);
    }

    // ✅ Reset the password using token
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token);
        if (user == null) {
            throw new RuntimeException("Invalid or expired password reset token");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // clear token after use
        userRepository.save(user);
    }

    // ✅ For verifying email during signup
    public void verifyUser(String token) {
        User user = userRepository.findByVerificationToken(token);
        if (user == null) {
            throw new RuntimeException("Invalid verification token");
        }
        user.setVerificationToken(null); // clear token after verification
        user.setEnabled(true);
        userRepository.save(user);
    }

    // Optional: find user by email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
