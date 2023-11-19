package com.template.springbootbackend.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.template.springbootbackend.models.entities.Client;
import com.template.springbootbackend.models.entities.Region;
import com.template.springbootbackend.models.services.ClientService;
import com.template.springbootbackend.models.services.RegionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" })
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private RegionService regionService;

    @GetMapping("/clients")
    public List<Client> getClients() {
        return clientService.findAll();
    }

    @GetMapping("/clients/page/{page}")
    public Page<Client> getClientPages(@PathVariable Integer page) {
        return clientService.findAll(PageRequest.of(page, 5));
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
    public ResponseEntity<?> save(@Valid @RequestBody Client client, BindingResult result) {
        Client clientNew = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("message", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

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
    public ResponseEntity<?> update(@Valid @RequestBody Client client, @PathVariable Long id, BindingResult result) {

        Map<String, Object> response = new HashMap<>();
        Client clientUpdated = null;
        Client clientSaved = clientService.findById(id);

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("message", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (clientSaved == null) {
            response.put("message", "The client id ".concat(id.toString()).concat(" doesn't exist in the database"));
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
            deleteClientPreviousPhotoFromServer(clientDeleted);
            clientService.deleteById(id);
        } catch (DataAccessException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The client has been deleted successfully");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/clients/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        Client client = clientService.findById(id);

        if (client == null) {
            response.put("message", "The client id ".concat(id.toString()).concat(" doesn't exist in the database"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID().toString();

            if (!fileName.endsWith(".png") || !fileName.endsWith(".jpg") || !fileName.endsWith(".jpeg")){
                fileName += ".png";
            }
        
            Path filePath = Paths.get("C:/Users/User/OneDrive/Diego PC - Oficina/Documentos/develop")
                    .resolve(fileName)
                    .toAbsolutePath();

            try {
                Files.copy(file.getInputStream(), filePath);
            } catch (IOException e) {
                response.put("message", "The photo didn't upload successfully");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            deleteClientPreviousPhotoFromServer(client);

            client.setPhoto(fileName);
            clientService.save(client);

            response.put("client", client);
            response.put("message", "The photo: " + fileName + " has been uploaded successfully");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        }

        response.put("message", "The file is empty");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }

    public void deleteClientPreviousPhotoFromServer(Client client) {
        String fileNameBefore = client.getPhoto();

        if (fileNameBefore != null && fileNameBefore.length() > 0) {
            Path filePathBefore = Paths.get("C:/Users/User/OneDrive/Diego PC - Oficina/Documentos/develop")
                    .resolve(fileNameBefore)
                    .toAbsolutePath();

            File fileBefore = filePathBefore.toFile();

            if (fileBefore.exists() && fileBefore.canRead()) {
                fileBefore.delete();
            }
        }
    }

    @GetMapping(value = "/uploads/img/{fileName:.+}")
    public ResponseEntity<Resource> showClientPhoto(@PathVariable String fileName) {
        Path filePath = Paths.get("C:/Users/User/OneDrive/Diego PC - Oficina/Documentos/develop")
                .resolve(fileName)
                .toAbsolutePath();
        Resource resource = null;

        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (!resource.exists() && !resource.isReadable()) {
            throw new RuntimeException("The photo " + fileName + " couldn't be loaded");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/clients/regions")
    public List<Region> getClientsRegions() {
        return regionService.findAll();
    }
}
