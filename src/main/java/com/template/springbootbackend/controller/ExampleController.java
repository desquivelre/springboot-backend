package com.template.springbootbackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ExampleController {

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello() {
        return "Hello, World!";
    }

    // Add more request mappings and methods as needed
}