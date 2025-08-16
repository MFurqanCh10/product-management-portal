package com.furqan.crud_demo_inventory.controller;

import com.furqan.crud_demo_inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ResetPasswordController {

    @Autowired
    private UserService userService;

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password"; // thymeleaf page
    }

    @PostMapping("/reset-password")
    public String processResetPassword(
            @RequestParam("token") String token,
            @RequestParam("password") String password,
            Model model) {

        try {
            userService.resetPassword(token, password);
            model.addAttribute("message", "Your password has been successfully reset. You can now login.");
            return "login"; // redirect to login after reset
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "reset-password";
        }
    }
}
