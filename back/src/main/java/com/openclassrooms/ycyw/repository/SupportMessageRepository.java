package com.openclassrooms.ycyw.repository;
import org.springframework.data.repository.CrudRepository;


import com.openclassrooms.ycyw.model.SupportMessageEntity;

public interface SupportMessageRepository extends CrudRepository<SupportMessageEntity, Long> {
    
}
