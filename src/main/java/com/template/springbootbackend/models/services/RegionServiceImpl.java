package com.template.springbootbackend.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.template.springbootbackend.models.dao.RegionDaoRepository;
import com.template.springbootbackend.models.entities.Region;

@Service
public class RegionServiceImpl implements RegionService{
    
    @Autowired
    private RegionDaoRepository regionDaoRepository;

    @Override
    public List<Region> findAll() {
        return regionDaoRepository.findAll();
    }
    
}
