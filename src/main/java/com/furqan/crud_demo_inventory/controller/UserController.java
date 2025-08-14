package com.furqan.crud_demo_inventory.controller;

import com.furqan.crud_demo_inventory.entity.Role;
import com.furqan.crud_demo_inventory.entity.User;
import com.furqan.crud_demo_inventory.repository.RoleRepository;
import com.furqan.crud_demo_inventory.repository.UserRepository;
import com.furqan.crud_demo_inventory.service.AuthService;
import com.furqan.crud_demo_inventory.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthService authService;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(
            @ModelAttribute User user,
            @RequestParam("selectedRole") String selectedRole,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("errorMessage", "This email is already registered!");
            return "signup"; // stay on signup page
        }

        // Disable account until verification
        user.setEnabled(false);
        user.setVerificationToken(UUID.randomUUID().toString());

        // Ensure role matches DB format (ROLE_MANAGER / ROLE_EMPLOYEE)
        String dbRoleName = selectedRole.startsWith("ROLE_") ? selectedRole : "ROLE_" + selectedRole;

        // Find role by name safely
        Optional<Role> roleOpt = roleRepository.findByRoleName(dbRoleName);
        if (roleOpt.isEmpty()) {
            throw new RuntimeException("Role not found: " + dbRoleName);
        }

        // Assign role
        user.getRoles().add(roleOpt.get());

        // Save user with encoded password
        authService.registerUser(user);

        // Send verification email
        String siteURL = request.getRequestURL().toString().replace(request.getServletPath(), "");
        emailService.sendVerificationEmail(user, siteURL);

        // Redirect to login with success message
        redirectAttributes.addFlashAttribute("successMessage", "A verification link has been sent to your email.");
        return "redirect:/login";
    }

    @Transactional
    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token) {
        User user = userRepository.findByVerificationToken(token);
        if (user != null) {
            user.setEnabled(true);
            user.setVerificationToken(null);
            userRepository.save(user);
            return "verify-success";
        } else {
            return "verify-fail";
        }
    }
}
