package com.template.springbootbackend.infraestructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.template.springbootbackend.infraestructure.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
