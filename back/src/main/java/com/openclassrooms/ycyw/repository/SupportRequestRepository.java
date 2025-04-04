package com.openclassrooms.ycyw.repository;

import org.springframework.data.repository.CrudRepository;

import com.openclassrooms.ycyw.model.SupportRequestEntity;

public interface SupportRequestRepository extends CrudRepository<SupportRequestEntity, Long> {
    
}
