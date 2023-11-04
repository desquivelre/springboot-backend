package com.template.springbootbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.template.springbootbackend.models.entities.Client;

public interface ClientDaoRepository extends JpaRepository<Client, Long> {

}
