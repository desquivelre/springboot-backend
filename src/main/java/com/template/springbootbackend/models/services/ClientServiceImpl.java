package com.template.springbootbackend.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.template.springbootbackend.models.entities.Client;
import com.template.springbootbackend.repositories.ClientDaoRepository;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientDaoRepository clientDaoRepository;

    @Override
    public List<Client> findAll() {
        return clientDaoRepository.findAll();
    }

    @Override
    public Client save(Client client) {
        return clientDaoRepository.save(client);
    }

    @Override
    public Client findById(Long id) {
        return clientDaoRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        clientDaoRepository.deleteById(id);
    }

    
}