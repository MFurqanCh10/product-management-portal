package com.furqan.crud_demo_inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage(Model model) {
        // Now Thymeleaf can access flash attributes like successMessage
        return "fancy-login";
    }

    // Add this to handle /login URL
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "fancy-login";
    }
}
