package com.example.iotestapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.iotestapp.form.InputForm;

@Controller
public class OutputController {

    @PostMapping("/output")
    public String output(@RequestParam(required = false) String accountName,
            InputForm inputForm, Model model) {
        model.addAttribute("accountName", accountName);
        model.addAttribute("inputForm", inputForm);
        return "output";
    }
}