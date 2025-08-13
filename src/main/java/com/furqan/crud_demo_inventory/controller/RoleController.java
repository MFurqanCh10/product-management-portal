package com.furqan.crud_demo_inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoleController {

    @GetMapping("/admin-role")
    public String adminPage() {
        return "admin-role";
    }

    @GetMapping("/manager-role")
    public String managerPage() {
        return "manager-role";
    }

    @GetMapping("/employ-role")
    public String employPage() {
        return "employ-role";
    }
}
