package com.template.springbootbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.template.springbootbackend.models.entities.Client;
import com.template.springbootbackend.models.services.ClientService;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<Client> index(){
        Client client = new Client(12358963, "Diego", "Esquivel", "a@xyz.com", 963126598);
        return clientService.findAll();
    }
}
