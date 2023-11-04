package com.template.springbootbackend.models.services;

import java.util.List;

import com.template.springbootbackend.models.entities.Client;

public interface ClientService {
    public List<Client> findAll();
}
