package com.echoteam.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/home")
public class HomePageController {

    @GetMapping("/workers")
    private String getHomePage() {
        return "home-page";
    }

}
