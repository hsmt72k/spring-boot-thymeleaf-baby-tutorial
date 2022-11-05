package com.example.iotestapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InputController {
    
    @GetMapping("/input")
    public String input() {
        return "input";
    }
}
