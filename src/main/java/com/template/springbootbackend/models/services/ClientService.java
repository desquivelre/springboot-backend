package com.template.springbootbackend.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.template.springbootbackend.models.entities.Client;

public interface ClientService {
    public List<Client> findAll();
    public Page<Client> findAll(Pageable pageable);
    public Client save(Client client);
    public Client findById(Long id);
    public void deleteById(Long id);
}
