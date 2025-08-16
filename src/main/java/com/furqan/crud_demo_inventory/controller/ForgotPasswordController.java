package com.furqan.crud_demo_inventory.controller;

import com.furqan.crud_demo_inventory.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password"; // thymeleaf page
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(
            @RequestParam("email") String email,
            HttpServletRequest request,
            Model model) {

        try {
            String siteURL = getSiteURL(request);
            userService.processForgotPassword(email, siteURL);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "forgot-password";
    }

    private String getSiteURL(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.replace(request.getServletPath(), "");
    }
}
