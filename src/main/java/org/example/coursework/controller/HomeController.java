package org.example.coursework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Проверьте, что у вас есть файл index.html в директории resources/templates
    }
}//
