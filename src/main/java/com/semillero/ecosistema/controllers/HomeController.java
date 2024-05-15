package com.semillero.ecosistema.controllers;

import com.semillero.ecosistema.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    UserAuthService userAuthService;

    @GetMapping("/")
    public String home() {
        return "Hello, home";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Hello, Secured";
    }

    @GetMapping("/provider")
    public String provider() {
        return "Hello, Provider";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello, Admin";
    }
}
