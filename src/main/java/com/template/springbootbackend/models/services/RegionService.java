package com.template.springbootbackend.models.services;

import java.util.List;

import com.template.springbootbackend.models.entities.Region;

public interface RegionService {
    public List<Region> findAll();
}
