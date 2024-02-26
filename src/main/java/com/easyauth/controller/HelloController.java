package com.easyauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@RestController
@Tag(name = "Hello")
public class HelloController {

    @GetMapping("/hello/{id}")
    @Operation(summary = "Hello", description = "Hello World")
    public String hello(HttpServletRequest request, @PathVariable String id) {
        return "Hello World " + id;
    }
}
