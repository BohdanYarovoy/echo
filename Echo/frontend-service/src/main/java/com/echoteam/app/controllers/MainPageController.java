package com.echoteam.app.controllers;

import com.echoteam.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/web/main", "/"})
@RequiredArgsConstructor
public class MainPageController {
    private final UserRepository userRepository;

    @GetMapping
    private String getMainPage() {
        return "main-pages";
    }

}
