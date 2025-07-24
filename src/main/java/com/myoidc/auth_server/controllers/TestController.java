package com.myoidc.auth_server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/protected")
    public String showHomePage() {
        return "protected";
    }
}
