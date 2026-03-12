package com.keycloak.keycloak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() { 
        return "login"; 
    } 
 
    @GetMapping("/profile") 
    public String profile() { 
        return "profile"; 
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/401")
    public String authen() {
        return "401";
    }
}
