package com.template.springbootbackend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.template.springbootbackend.models.entities.Client;
import com.template.springbootbackend.models.services.ClientService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"*"})
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<Client> index(){
        return clientService.findAll();
    }

    @GetMapping("/clients/{id}")
    public Client getClientById(@PathVariable Long id){
        return clientService.findById(id);
    }

    @PostMapping("/clients/save")
    public Client save(@RequestBody Client client){
        return clientService.save(client);
    }
}
