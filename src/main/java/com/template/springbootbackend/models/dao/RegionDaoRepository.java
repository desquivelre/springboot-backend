package com.template.springbootbackend.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.template.springbootbackend.models.entities.Region;

public interface RegionDaoRepository extends JpaRepository<Region, Long>{
    
}
