package com.apps.cli.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cli")
public class CliController {

    @GetMapping("/test")
    public String test() {
        return "CLI Service is running successfully 🚀";
    }

    @GetMapping("/add")
    public String add(@RequestParam int a, @RequestParam int b) {
        return "Result: " + (a + b);
    }

    @GetMapping("/multiply")
    public String multiply(@RequestParam int a, @RequestParam int b) {
        return "Result: " + (a * b);
    }
}
