package com.furqan.crud_demo_inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/showMyLoginPage")

    public String showMyLoginPage(){

        return "fancy-login";
    }
}
