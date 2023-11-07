package com.template.springbootbackend.models.services;

import java.util.List;

import com.template.springbootbackend.models.entities.Client;

public interface ClientService {
    public List<Client> findAll();
    public Client save(Client client);
    public Client findById(Long id);
    public void deleteById(Long id);
}
