package com.template.springbootbackend.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.template.springbootbackend.models.dao.ClientDaoRepository;
import com.template.springbootbackend.models.entities.Client;

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

    @Override
    public Page<Client> findAll(Pageable pageable) {
        return clientDaoRepository.findAll(pageable);
    }
}