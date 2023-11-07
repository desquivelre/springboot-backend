package com.template.springbootbackend.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.template.springbootbackend.models.entities.Client;
import com.template.springbootbackend.models.services.ClientService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" })
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<Client> index() {
        return clientService.findAll();
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        Client client = null;
        Map<String, Object> response = new HashMap<>();

        try {
            client = clientService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (client == null) {
            response.put("message", "The client id ".concat(id.toString()).concat(" doesn't exist in the database"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @PostMapping("/clients")
    public ResponseEntity<?> save(@RequestBody Client client) {
        Client clientNew = null;
        Map<String, Object> response = new HashMap<>();

        try {
            clientNew = clientService.save(client);
        } catch (DataAccessException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The client has been created successfully");
        response.put("client", clientNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<?> update(@RequestBody Client client, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        Client clientUpdated = null;
        Client clientSaved = clientService.findById(id);

        if (clientSaved == null) {
            response.put("message", "The client id ".concat(id.toString()).concat(" doesn't exist in the database"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if (client.getCellphoneNumber() == 0 || client.getDni() == 0) {
            response.put("message", "The client data provided doesn't match those requested");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clientSaved.setDni(client.getDni());
            clientSaved.setFirstNames(client.getFirstNames());
            clientSaved.setLastNames(client.getLastNames());
            clientSaved.setEmail(client.getEmail());
            clientSaved.setCellphoneNumber(client.getCellphoneNumber());
            clientSaved.setModificatedAt(LocalDate.now());

            clientUpdated = clientService.save(clientSaved);
        } catch (DataAccessException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The client has been updated successfully");
        response.put("client", clientUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Client clientDeleted = clientService.findById(id);

        if (clientDeleted == null) {
            response.put("message", "The client id ".concat(id.toString()).concat(" doesn't exist in the database"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clientService.deleteById(id);
        } catch (DataAccessException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The client has been deleted successfully");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
